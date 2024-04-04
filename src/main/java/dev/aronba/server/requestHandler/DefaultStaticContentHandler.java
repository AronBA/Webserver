package dev.aronba.server.requestHandler;

import dev.aronba.server.ServerConfigReader;
import dev.aronba.server.exception.RequestHandlerException;
import dev.aronba.server.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class DefaultStaticContentHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public HttpResponse handle(HttpRequest request) throws RequestHandlerException {


        try {

            String fileName = request.getRequestUrl();
            URL resourceURL = DefaultStaticContentHandler.class.getClassLoader().getResource(fileName);

            if (resourceURL == null) {
                logger.warn("File couldn't be found on server: " + fileName);

                String body = ServerConfigReader.DEFAULT_NOT_FOUND_BODY;

                Map<String, String> header = new HashMap<>();
                header.put(HttpHeader.DATE.getHeaderName(), LocalDateTime.now().toString());
                header.put(HttpHeader.CONTENT_TYPE.getHeaderName(), HttpContentType.TEXT_HTML.getContentType());
                header.put(HttpHeader.CONTENT_LENGTH.getHeaderName(), String.valueOf(body.getBytes(StandardCharsets.UTF_8).length));
                header.put(HttpHeader.CACHE_CONTROL.getHeaderName(), HttpCacheControl.NO_CACHE.getDirective());

                return HttpResponse.builder().httpVersion(HttpVersion.HTTP_1_1).httpStatusCode(HttpStatusCode.NOT_FOUND).header(header).body(body).build();
            }

            Path filePath = Paths.get(resourceURL.toURI());
            String body = Files.readString(filePath);


            return HttpResponse.builder().httpVersion(HttpVersion.HTTP_1_1).httpStatusCode(HttpStatusCode.OK).body(body).header(HttpHeader.DEFAULT_HEADER).build();
        } catch (Exception e) {
            logger.error("something went wrong during static content handling" + e.getMessage());
            throw new RequestHandlerException();
        }
    }
}
