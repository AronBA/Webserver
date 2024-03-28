package dev.aronba.server.requestHandler;


import dev.aronba.server.http.HttpRequest;
import dev.aronba.server.http.HttpResponse;

@FunctionalInterface
public interface RequestHandler {
    HttpResponse handle(HttpRequest request);
}
