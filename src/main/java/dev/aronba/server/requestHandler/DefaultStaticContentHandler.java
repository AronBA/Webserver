package dev.aronba.server.requestHandler;

import dev.aronba.server.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DefaultStaticContentHandler implements RequestHandler{

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);
    @Override
    public HttpResponse handle(HttpRequest request) {


        try{

            String fileName = request.getRequestUrl();
            URL  resourceURL = DefaultStaticContentHandler.class.getClassLoader().getResource(fileName);

            if (resourceURL == null){
                logger.warn("File couldn't be found on server: " + fileName);
                return null;
            }

            Path filePath = Paths.get(resourceURL.toURI());
            String body = Files.readString(filePath);


            return HttpResponse.builder()
                    .httpVersion(HttpVersion.HTTP_1_1)
                    .httpStatusCode(HttpStatusCode.OK)
                    .body(body)
                    .header(HttpHeader.DEFAULT_HEADER)
                    .build();
        } catch (Exception e){
            logger.error("something went wrong during static content handling" + e.getMessage());
            return null;
        }
    }
}
