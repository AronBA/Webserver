package dev.aronba;

import dev.aronba.constant.HttpMethod;
import dev.aronba.constant.ServerState;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final Map<String, HttpHandler> httpHandlerMap = new ConcurrentHashMap<>();
    private final InetSocketAddress inetSocketAddress;
    private final Dispatcher dispatcher;
    private final Set<HttpConnection> allServerConnections;
    private final ServerSocket serverSocket;
    private ExecutorService threadPoolExecutor;
    private Thread dispatcherThread;
    private ServerState state;

    HttpServer(InetSocketAddress inetSocketAddress) throws IOException {
        if (inetSocketAddress == null) {
            throw new IllegalArgumentException("InetSocketAddress cannot be null");
        }
        this.inetSocketAddress = inetSocketAddress;
        this.dispatcher = new Dispatcher(this);
        this.state = ServerState.STOPPED;
        this.allServerConnections = Collections.synchronizedSet(new HashSet<HttpConnection>());
        this.serverSocket = new ServerSocket(inetSocketAddress.getPort());
    }

    public Map<String, HttpHandler> getHttpHandlerMap() {
        return httpHandlerMap;
    }

    public void start() {
        if (threadPoolExecutor == null) {
            this.threadPoolExecutor = Executors.newFixedThreadPool(4);
        }
        this.dispatcherThread = new Thread(null, dispatcher, "HTTP-Dispatcher", 0, false);
        this.state = ServerState.RUNNING;
        dispatcherThread.start();

    }

    public void stop(int delay) {
        if (delay < 0) {
            throw new IllegalArgumentException("negative delay parameter are not supported (yet)");
        }
        this.state = ServerState.TERMINATING;

        if (dispatcherThread != null && dispatcherThread != Thread.currentThread()) {

            try {
                dispatcherThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (HttpConnection connection : allServerConnections) {
                connection.close();
            }
            allServerConnections.clear();
            httpHandlerMap.clear();
        }
        this.state = ServerState.TERMINATED;
    }

    public void addMapping(HttpMethod httpMethod, URL url, HttpHandler httpHandler) {
        String key = httpMethod.toString() + "_" + url.toString();
        if (httpHandlerMap.containsKey(key)) {
            throw new IllegalArgumentException("Mapping for " + key + " already exists");
        }
        this.httpHandlerMap.put(key, httpHandler);
    }

    private boolean isRunning() {
        return this.state == ServerState.RUNNING;
    }

    private boolean isStopped() {
        return this.state == ServerState.STOPPED;
    }

    private boolean isTerminating() {
        return this.state == ServerState.TERMINATING;
    }

    private boolean isTerminated() {
        return this.state == ServerState.TERMINATED;
    }

    public InetSocketAddress getInetSocketAddress() {
        return inetSocketAddress;
    }

    public ExecutorService getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public Thread getDispatcherThread() {
        return dispatcherThread;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public ServerState getState() {
        return state;
    }

    public Set<HttpConnection> getAllServerConnections() {
        return allServerConnections;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
