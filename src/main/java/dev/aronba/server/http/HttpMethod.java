package dev.aronba.server.http;




public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    HEAD,
    OPTIONS,
    TRACE,
    CONNECT;

    public String toString() {
        return "HttpMethod()";
    }
}
