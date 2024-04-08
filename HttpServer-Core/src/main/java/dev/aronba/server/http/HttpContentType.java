package dev.aronba.server.http;

public enum HttpContentType {

    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html"),
    TEXT_CSS("text/css"),
    APPLICATION_JSON("application/json"),
    APPLICATION_XML("application/xml"),
    APPLICATION_PDF("application/pdf"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_GIF("image/gif"),
    APPLICATION_OCTET_STREAM("application/octet-stream");

    private final String contentType;

    private HttpContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return this.contentType;
    }

    public String toString() {
        return "HttpContentType(contentType=" + this.getContentType() + ")";
    }
}
