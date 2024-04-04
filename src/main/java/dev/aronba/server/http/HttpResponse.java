package dev.aronba.server.http;

import dev.aronba.server.HttpServerConfigReader;
import dev.aronba.server.requestHandler.DefaultStaticContentHandler;
import lombok.Builder;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;


@Builder
@Data
public class HttpResponse {
    private final static Logger LOG = LoggerFactory.getLogger(HttpResponse.class);

    HttpVersion httpVersion;
    HttpStatusCode httpStatusCode;
    String body;
    Map<String, String> header;


    public static HttpResponse NO_CONTENT() {
        return null;
    }

    public static HttpResponse NOT_FOUND() {
        return null;
    }

    public static HttpResponse INTERNAL_SERVER_ERROR( ) {

        try{

            return new DefaultStaticContentHandler().handle(HttpRequest.builder().requestUrl(HttpServerConfigReader.DEFAULT_ERROR_PAGE_PATH).build());
        }catch (Exception e){
            LOG.error("Something went wrong during the creation of the error response. hint: Check your standard error page config. " +Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

}
