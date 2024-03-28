package dev.aronba.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
    private final HttpServer httpServer;

    Dispatcher(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void run() {
        logger.info("Dispatch Thread started and listening");
        while (this.httpServer.getState() == ServerState.RUNNING) {
            try {
                Socket socket = this.httpServer.getServerSocket().accept();
                logger.info(socket.getInetAddress()+" has connected");
                this.httpServer.getThreadPoolExecutor().submit(() -> {

                    HttpConnection httpConnection = new HttpConnection(socket);
                    httpConnection.handle(httpServer.getHttpHandlerMap());
                    httpConnection.close();
                    logger.info(socket.getInetAddress()+" has disconnected");

                });

            } catch (IOException e) {
                logger.error(e.toString());
            }


        }
    }

}
