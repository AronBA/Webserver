package dev.aronba.server.requestHandler;


import dev.aronba.server.exception.RequestHandlerException;
import dev.aronba.server.http.HttpRequest;
import dev.aronba.server.http.HttpResponse;

import java.util.Optional;

@FunctionalInterface
public interface RequestHandler {

    /**
     * @param request the incoming {@link HttpRequest} which is to be handled
     * @return will return a valid {@link HttpResponse}
     * @throws RequestHandlerException is thrown when the request couldn't be processed by the implementation
     */
    HttpResponse handle(HttpRequest request) throws RequestHandlerException;
}
