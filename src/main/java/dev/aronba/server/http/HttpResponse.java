package dev.aronba.server.http;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;


@Builder
@Data
public class HttpResponse {

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

    public static HttpResponse INTERNAL_SERVER_ERROR(String errorMessage) {

        UUID traceID = UUID.randomUUID();

        return null;
    }

}
