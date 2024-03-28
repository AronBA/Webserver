package dev.aronba.server.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HttpRequest {
    HttpMethod httpMethod;
    String httpVersion;
    String requestUrl;
    Map<String,String> header;
    String body;
}
