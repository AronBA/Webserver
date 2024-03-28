package dev.aronba.server;

import dev.aronba.server.http.HttpMethod;
import dev.aronba.server.http.HttpRequest;
import dev.aronba.server.http.HttpResponse;
import dev.aronba.server.requestHandler.DefaultNotFoundHandler;
import dev.aronba.server.requestHandler.DefaultStaticContentHandler;
import dev.aronba.server.requestHandler.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


public class HttpConnection {
    private static final Logger logger = LoggerFactory.getLogger(HttpConnection.class);
    private final Socket socket;
    private HttpRequest httpRequest;

    private InputStream inputStream;
    private OutputStream outputStream;

    public HttpConnection(Socket socket) {
        this.socket = socket;
    }

    public void close() {
        logger.debug("closing connection for: " + this.socket.getInetAddress());
        if (!this.socket.isClosed()) {
            try {
                this.inputStream.close();
                this.outputStream.close();
                this.socket.close();
            } catch (IOException e) {
            }
        }
    }

    public void handle(Map<String, RequestHandler> httpHandlerMap) {
        logger.debug("started handling request for: " + this.socket.getInetAddress());

        this.httpRequest = parseRequest();
        String key = getKey();
        RequestHandler requestHandler = httpHandlerMap.get(key);


        // if no handler is found try to load static content
        if (requestHandler == null) {
            requestHandler = new DefaultStaticContentHandler();
        }
        HttpResponse httpResponse = requestHandler.handle(httpRequest);

        // if there is no response send 404
        if (httpResponse == null) {
            requestHandler = new DefaultNotFoundHandler();
            httpResponse = requestHandler.handle(httpRequest);
        }
        logger.debug("used request handler: " + requestHandler);
        sendResponse(httpResponse);
    }

    private void sendResponse(HttpResponse httpResponse) {
        try {

            StringBuilder responseBuilder = new StringBuilder();

             this.outputStream = this.socket.getOutputStream();

            responseBuilder.append(httpResponse.getHttpVersion())
                    .append(" ")
                    .append(httpResponse.getHttpStatusCode().getCode())
                    .append(" ")
                    .append(httpResponse.getHttpStatusCode().getReasonPhrase())
                    .append("\r\n");

            for (Map.Entry<String, String> entry : httpResponse.getHeader().entrySet()) {
                responseBuilder.append(entry.getKey())
                        .append(": ")
                        .append(entry.getValue())
                        .append("\r\n");
            }
            responseBuilder.append("\r\n")
                    .append(httpResponse.getBody())
                    .append("\r\n")
                    .append("\r\n");

            byte[] responseData = responseBuilder.toString().getBytes(StandardCharsets.UTF_8);
            outputStream.write(responseData);
            outputStream.flush();

        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    private HttpRequest parseRequest() {
        logger.debug("started parsing request for: " + this.socket.getInetAddress());
        try {
             this.inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String requestLine = reader.readLine();
            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String requestURL = requestParts[1];
            String httpVersion = requestParts[2];

            Map<String, String> headers = new HashMap<>();
            String headerLine;
            while ((headerLine = reader.readLine()) != null && !headerLine.isEmpty()) {
                int separatorIndex = headerLine.indexOf(':');
                if (separatorIndex != -1) {
                    String headerName = headerLine.substring(0, separatorIndex).trim();
                    String headerValue = headerLine.substring(separatorIndex + 1).trim();
                    headers.put(headerName, headerValue);
                }
            }

            StringBuilder bodyBuilder = new StringBuilder();
            while (reader.ready()) {
                bodyBuilder.append((char) reader.read());
            }
            String body = bodyBuilder.toString().trim();

            HttpRequest request = new HttpRequest(HttpMethod.valueOf(method), httpVersion, requestURL, headers, body);

            logger.debug("parsed to: " + request);
            return request;
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }

    }

    private String getKey() {
        String key = this.httpRequest.getHttpMethod() + "_" + this.httpRequest.getRequestUrl();
        logger.debug("key: " + key);
        return key;
    }
}
