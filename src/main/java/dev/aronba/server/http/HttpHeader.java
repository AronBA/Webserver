package dev.aronba.server.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;


@Getter
@ToString
@RequiredArgsConstructor
public enum HttpHeader {
    ACCEPT("Accept"),
    ACCEPT_CHARSET("Accept-Charset"),
    ACCEPT_ENCODING("Accept-Encoding"),
    ACCEPT_LANGUAGE("Accept-Language"),
    ACCEPT_DATETIME("Accept-Datetime"),
    AUTHORIZATION("Authorization"),
    CACHE_CONTROL("Cache-Control"),
    CONNECTION("Connection"),
    COOKIE("Cookie"),
    CONTENT_LENGTH("Content-Length"),
    CONTENT_TYPE("Content-Type"),
    DATE("Date"),
    EXPECT("Expect"),
    FROM("From"),
    HOST("Host"),
    IF_MATCH("If-Match"),
    IF_MODIFIED_SINCE("If-Modified-Since"),
    IF_NONE_MATCH("If-None-Match"),
    IF_RANGE("If-Range"),
    IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
    MAX_FORWARDS("Max-Forwards"),
    ORIGIN("Origin"),
    PRAGMA("Pragma"),
    PROXY_AUTHORIZATION("Proxy-Authorization"),
    RANGE("Range"),
    REFERER("Referer"),
    TE("TE"),
    USER_AGENT("User-Agent"),
    UPGRADE("Upgrade"),
    VIA("Via"),
    WARNING("Warning"),
    WEBSOCKET_EXTENSIONS("WebSocket-Extensions"),
    WEBSOCKET_KEY("WebSocket-Key"),
    WEBSOCKET_VERSION("WebSocket-Version"),
    X_REQUESTED_WITH("X-Requested-With"),
    X_FORWARDED_FOR("X-Forwarded-For"),
    X_FORWARDED_HOST("X-Forwarded-Host"),
    X_FORWARDED_PROTO("X-Forwarded-Proto");

    private final String headerName;

    public static final Map<String,String> DEFAULT_HEADER = new HashMap<>();
}
