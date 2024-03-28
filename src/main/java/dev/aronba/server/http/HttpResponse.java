package dev.aronba.server.http;

import lombok.Builder;
import lombok.Data;

import java.util.Map;


@Builder
@Data
public class HttpResponse {

    HttpVersion httpVersion;
    HttpStatusCode httpStatusCode;
    String body;
    Map<String, String> header;
}
