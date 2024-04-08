package dev.aronba.server.requestHandler;

import dev.aronba.server.HttpServerConfigReader;
import dev.aronba.server.exception.RequestHandlerException;
import dev.aronba.server.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class DefaultStaticContentHandler implements RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    @Override
    public HttpResponse handle(HttpRequest request) throws RequestHandlerException {

        try {

            String requestedUrl = request.getRequestUrl();
            if (requestedUrl.equals("/")) {
                requestedUrl = HttpServerConfigReader.INDEX_FILE;
            }
            String filePath = HttpServerConfigReader.ROOT_DIRECTORY + requestedUrl;
            String body = Files.readString(Paths.get(filePath));

            return HttpResponse.builder().httpVersion(HttpVersion.HTTP_1_1).httpStatusCode(HttpStatusCode.OK).body(body).header(HttpHeader.DEFAULT_HEADER).build();
        } catch (Exception e) {
            logger.error("something went wrong during static content handling: " + Arrays.toString(e.getStackTrace()));
            throw new RequestHandlerException();
        }
    }
}
