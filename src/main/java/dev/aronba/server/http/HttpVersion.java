package dev.aronba.server.http;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


public enum HttpVersion {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0");

    private final String version;

    private HttpVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public String toString() {
        return "HttpVersion(version=" + this.getVersion() + ")";
    }
}
