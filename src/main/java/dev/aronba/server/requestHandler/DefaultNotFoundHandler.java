package dev.aronba.server.requestHandler;

import dev.aronba.server.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DefaultNotFoundHandler implements RequestHandler {
    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    @Override
    public HttpResponse handle(HttpRequest request) {

        String body = "<html>" +
                "<header>" +
                "<title>" +
                "404 Not Found" +
                "</title>" +
                "</header>" +
                "<body>"+
                "<h1> 404 Here is nothing </h1>" +
                "</body>"+
                "</html>";

        Map<String, String> header = new HashMap<>();
        header.put(HttpHeader.DATE.getHeaderName(), LocalDateTime.now().toString());
        header.put(HttpHeader.CONTENT_TYPE.getHeaderName(), HttpContentType.TEXT_HTML.getContentType());
        header.put(HttpHeader.CONTENT_LENGTH.getHeaderName(), String.valueOf(body.getBytes(StandardCharsets.UTF_8).length));
        header.put(HttpHeader.CACHE_CONTROL.getHeaderName(), HttpCacheControl.NO_CACHE.getDirective());

        return HttpResponse.builder().httpVersion(HttpVersion.HTTP_1_1).httpStatusCode(HttpStatusCode.NOT_FOUND).header(header).body(body).build();
    }
}
