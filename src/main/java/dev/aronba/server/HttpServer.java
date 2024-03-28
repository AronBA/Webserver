package dev.aronba.server;

import dev.aronba.server.exception.InvalidServerStateException;
import dev.aronba.server.http.HttpMethod;
import dev.aronba.server.requestHandler.RequestHandler;
import lombok.Getter;
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


@Getter
public class HttpServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private final Map<String, RequestHandler> httpHandlerMap = new ConcurrentHashMap<>();
    private final InetSocketAddress inetSocketAddress;
    private final Dispatcher dispatcher;
    private final Set<HttpConnection> allServerConnections;
    private final ServerSocket serverSocket;
    private ExecutorService threadPoolExecutor;
    private Thread dispatcherThread;
    private ServerState state;

    public HttpServer(InetSocketAddress inetSocketAddress) throws IOException {
        if (inetSocketAddress == null) {
            throw new IllegalArgumentException("InetSocketAddress cannot be null");
        }
        this.inetSocketAddress = inetSocketAddress;
        this.dispatcher = new Dispatcher(this);
        this.state = ServerState.STOPPED;
        this.allServerConnections = Collections.synchronizedSet(new HashSet<>());
        this.serverSocket = new ServerSocket(inetSocketAddress.getPort());
    }

    public void start() {
        logger.info("HttpServer starting");
        if (threadPoolExecutor == null) {
            this.threadPoolExecutor = Executors.newFixedThreadPool(4);
        }
        this.dispatcherThread = new Thread(null, dispatcher, "HTTP-Dispatcher", 0, false);
        this.state = ServerState.RUNNING;
        dispatcherThread.start();
        logger.info("HttpServer started and listening on port: " + this.getInetSocketAddress().getPort());
    }

    public void stop() {
        logger.info("HttpServer shutdown initialized");
        this.state = ServerState.TERMINATING;

        try {
            if (dispatcherThread != null && dispatcherThread != Thread.currentThread()) {

                dispatcherThread.join();

                for (HttpConnection connection : allServerConnections) {
                    connection.close();
                }
                allServerConnections.clear();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.state = ServerState.TERMINATED;
        logger.info("HttpServer terminated");
    }

    public void addRequestMapping(HttpMethod httpMethod, String url, RequestHandler requestHandler) {

        if (this.state != ServerState.STOPPED){
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

    public void addRequestMapping(RequestMapping requestMapping) {


        if (this.state != ServerState.STOPPED){
            throw new InvalidServerStateException("Server needs  to be stopped to add new Mappings");
        }

        final String key = requestMapping.getHttpMethod() + "_" + requestMapping.getUrl();
        if (requestMapping.getUrl() == null) {
            throw new IllegalArgumentException("URL cannot  be null");
        }
        if (requestMapping.getRequestHandler() == null) {
            throw new IllegalArgumentException("Request handler cannot  be null");
        }
        if (httpHandlerMap.containsKey(key)) {
            throw new IllegalArgumentException("Mapping for " + key + " already exists");
        }
        this.httpHandlerMap.put(key, requestMapping.getRequestHandler());
    }

}
