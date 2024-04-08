package dev.aronba.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

class DispatcherService implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherService.class);
    private final HttpServer httpServer;

    public DispatcherService(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void run() {
        logger.info("Dispatch Thread started and listening");
        while (this.httpServer.getState() == HttpServerState.RUNNING) {
            try {
                Socket socket = this.httpServer.getServerSocket().accept();
                logger.info(socket.getInetAddress() + " has connected");
                this.httpServer.getThreadPoolExecutor().submit(() -> {
                    HttpConnection httpConnection = new HttpConnection(socket);

                    this.httpServer.getAllServerConnections().add(httpConnection);

                    httpConnection.handle(httpServer.getHttpHandlerMap());

                    if (!HttpServerConfigReader.DEVELOPER_MODE){
                        httpConnection.close();
                        this.httpServer.getAllServerConnections().remove(httpConnection);
                        logger.info(socket.getInetAddress() + " has disconnected");
                    }


                });

            } catch (IOException e) {
                logger.error(e.toString());
            }


        }
    }

}
