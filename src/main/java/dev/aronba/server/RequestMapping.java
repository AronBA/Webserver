package dev.aronba.server;

import dev.aronba.server.http.HttpMethod;
import dev.aronba.server.requestHandler.RequestHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class RequestMapping {
    private HttpMethod httpMethod;
    private RequestHandler requestHandler;
    private String url;
}