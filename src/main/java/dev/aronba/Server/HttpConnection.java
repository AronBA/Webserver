package dev.aronba;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;


public class HttpConnection {
    private final Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public HttpConnection(Socket socket) {
        this.socket = socket;
    }

    public void close() {
        if (!this.socket.isClosed()) {
            try {
                this.socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handle(Map<String, HttpHandler> httpHandlerMap) {

        String key = getKey();
        HttpHandler httpHandler = httpHandlerMap.get(key);
        HttpRequest httpRequest = parseRequest();
        HttpResponse httpResponse =  httpHandler.handle(httpRequest);
        sendResponse(httpResponse);

    }

    private void sendResponse(HttpResponse httpResponse) {
        throw new RuntimeException("Not implemented");
    }
    private HttpRequest parseRequest() {
        throw new RuntimeException("Not implemented");
    }
    private String getKey() {
        throw new RuntimeException("Not implemented");
    }
}
