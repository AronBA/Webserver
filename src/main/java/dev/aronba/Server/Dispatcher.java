package dev.aronba;

import dev.aronba.constant.ServerState;

import java.io.IOException;
import java.net.Socket;

public class Dispatcher implements Runnable {
    private final HttpServer httpServer;

    Dispatcher(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    @Override
    public void run() {

        while (this.httpServer.getState() == ServerState.RUNNING) {
            try {
                Socket socket = this.httpServer.getServerSocket().accept();

                this.httpServer.getThreadPoolExecutor().submit(() -> {

                    HttpConnection httpConnection = new HttpConnection(socket);
                    httpConnection.handle(httpServer.getHttpHandlerMap());
                    httpConnection.close();

                });

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
    }

}
