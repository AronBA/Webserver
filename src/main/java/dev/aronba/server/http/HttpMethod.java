package dev.aronba.server.http;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE,
    HEAD,
    OPTIONS,
    TRACE,
    CONNECT
}
