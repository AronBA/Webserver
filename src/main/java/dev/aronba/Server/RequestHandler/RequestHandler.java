package dev.aronba;


@FunctionalInterface
public interface HttpHandler {
    HttpResponse handle(HttpRequest request);
}
