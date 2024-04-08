package dev.aronba.server;

import dev.aronba.server.exception.InvalidServerStateException;
import dev.aronba.server.http.HttpMethod;
import dev.aronba.server.requestHandler.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private final Map<String, RequestHandler> httpHandlerMap = new ConcurrentHashMap<>();
    private final InetSocketAddress inetSocketAddress;
    private final DispatcherService dispatcherService;
    private final Set<HttpConnection> allServerConnections;
    private final ServerSocket serverSocket;
    private ChangeDetectionService changeDetectionService;
    private ExecutorService threadPoolExecutor;
    private Thread dispatcherThread;
    private volatile HttpServerState state;
    private Thread fileSystemListenerThread;

    public HttpServer(InetSocketAddress inetSocketAddress) throws IOException {
        this(inetSocketAddress, false);
    }


    public HttpServer(InetSocketAddress inetSocketAddress, boolean developerMode) throws IOException {
        if (inetSocketAddress == null) {
            throw new IllegalArgumentException("InetSocketAddress cannot be null");
        }

        if (developerMode) {
            this.changeDetectionService = new ChangeDetectionService(this);
        }

        this.inetSocketAddress = inetSocketAddress;
        this.dispatcherService = new DispatcherService(this);
        this.state = HttpServerState.STOPPED;
        this.allServerConnections = Collections.synchronizedSet(new HashSet<>());
        this.serverSocket = new ServerSocket(inetSocketAddress.getPort());
    }
    private String getWelcomeString() {
        return """
                                      
                                      |----------------|
                |+++++++++++++++++++++| Kek Web Server |+++++++++++++++++++++|\s
                                      |----------------|
                              ___  __    ___       __   ________     \s
                             |\\  \\|\\  \\ |\\  \\     |\\  \\|\\   ____\\    \s
                             \\ \\  \\/  /|\\ \\  \\    \\ \\  \\ \\  \\___|_   \s
                              \\ \\   ___  \\ \\  \\  __\\ \\  \\ \\_____  \\  \s
                               \\ \\  \\\\ \\  \\ \\  \\|\\__\\_\\  \\|____|\\  \\ \s
                                \\ \\__\\\\ \\__\\ \\____________\\____\\_\\  \\\s
                                 \\|__| \\|__|\\|____________|\\_________\\
                                                          \\|_________|
                |++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++|\s
                 """
                + HttpServerConfigReader.getCurrentConfigAsString() +
                "|++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++| "
                ;
    }

    public void start() {

        logger.info("HttpServer starting");

        if (threadPoolExecutor == null) {
            this.threadPoolExecutor = Executors.newFixedThreadPool(6);
        }

        if (HttpServerConfigReader.DEVELOPER_MODE) {
            logger.warn("Developer mode is active");
            this.fileSystemListenerThread = new Thread(null, changeDetectionService, "FileSystemListener", 0, false);
            fileSystemListenerThread.start();
        }


        this.dispatcherThread = new Thread(null, dispatcherService, "HTTP-Dispatcher", 0, false);
        this.state = HttpServerState.RUNNING;
        dispatcherThread.start();
        logger.info(getWelcomeString());

    }


    /**
     * Gracefully terminates the {@link HttpServer} and closes all connections
     */
    public void stop() {
        logger.info("HttpServer shutdown initialized");
        this.state = HttpServerState.TERMINATING;


        if (HttpServerConfigReader.DEVELOPER_MODE && this.fileSystemListenerThread.isAlive()){
            this.fileSystemListenerThread.interrupt();
        }
        if (dispatcherThread != null && dispatcherThread != Thread.currentThread()) {

            dispatcherThread.interrupt();

            for (HttpConnection connection : allServerConnections) {
                connection.close();
            }
            allServerConnections.clear();
        }

        if (!this.serverSocket.isClosed()){
            try {
                this.serverSocket.close();
            } catch (IOException ignore) {
                // can be ignored
            }
        }

        this.state = HttpServerState.TERMINATED;
        logger.info("HttpServer terminated");

    }


    /**
     * Overrides the default {@link RequestHandler} of the webserver when a certain resource is requested
     *
     * @param httpMethod     the method used to request the resource
     * @param url            the url of the resource
     * @param requestHandler a custom implementation of the {@link RequestHandler} interface
     */
    public void addRequestMapping(HttpMethod httpMethod, String url, RequestHandler requestHandler) {

        if (this.state != HttpServerState.STOPPED) {
            throw new InvalidServerStateException("Server needs  to be stopped to add new Mappings");
        }

        final String key = httpMethod + "_" + url;
        if (url == null) {
            throw new IllegalArgumentException("URL cannot  be null");
        }
        if (requestHandler == null) {
            throw new IllegalArgumentException("Request handler cannot  be null");
        }
        if (httpHandlerMap.containsKey(key)) {
            throw new IllegalArgumentException("Mapping for " + key + " already exists");
        }
        this.httpHandlerMap.put(key, requestHandler);
    }

    public void scheduleHotReload() {
        for (HttpConnection connection : allServerConnections) {
            connection.reload();
        }
    }

    public Map<String, RequestHandler> getHttpHandlerMap() {
        return this.httpHandlerMap;
    }

    public InetSocketAddress getInetSocketAddress() {
        return this.inetSocketAddress;
    }

    public DispatcherService getDispatcher() {
        return this.dispatcherService;
    }

    public Set<HttpConnection> getAllServerConnections() {
        return this.allServerConnections;
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public ChangeDetectionService getFileSystemListener() {
        return this.changeDetectionService;
    }

    public ExecutorService getThreadPoolExecutor() {
        return this.threadPoolExecutor;
    }

    public Thread getDispatcherThread() {
        return this.dispatcherThread;
    }

    public HttpServerState getState() {
        return this.state;
    }

    public Thread getFileSystemListenerThread() {
        return this.fileSystemListenerThread;
    }
}

