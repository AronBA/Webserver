package dev.aronba.server.http;

import dev.aronba.server.HttpServerConfigReader;
import dev.aronba.server.requestHandler.DefaultStaticContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;


public class HttpResponse {
    private final static Logger LOG = LoggerFactory.getLogger(HttpResponse.class);

    HttpVersion httpVersion;
    HttpStatusCode httpStatusCode;
    String body;
    Map<String, String> header;

    HttpResponse(HttpVersion httpVersion, HttpStatusCode httpStatusCode, String body, Map<String, String> header) {
        this.httpVersion = httpVersion;
        this.httpStatusCode = httpStatusCode;
        this.body = body;
        this.header = header;
    }


    public static HttpResponse NO_CONTENT() {
        return null;
    }

    public static HttpResponse NOT_FOUND() {
        return null;
    }

    public static HttpResponse INTERNAL_SERVER_ERROR() {

        try {

            return new DefaultStaticContentHandler().handle(HttpRequest.builder().requestUrl(HttpServerConfigReader.DEFAULT_ERROR_PAGE_PATH).build());
        } catch (Exception e) {
            LOG.error("Something went wrong during the creation of the error response. hint: Check your standard error page config. " + Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public static HttpResponseBuilder builder() {
        return new HttpResponseBuilder();
    }

    public HttpVersion getHttpVersion() {
        return this.httpVersion;
    }

    public HttpStatusCode getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public String getBody() {
        return this.body;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public void setHttpVersion(HttpVersion httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof HttpResponse)) return false;
        final HttpResponse other = (HttpResponse) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$httpVersion = this.getHttpVersion();
        final Object other$httpVersion = other.getHttpVersion();
        if (this$httpVersion == null ? other$httpVersion != null : !this$httpVersion.equals(other$httpVersion))
            return false;
        final Object this$httpStatusCode = this.getHttpStatusCode();
        final Object other$httpStatusCode = other.getHttpStatusCode();
        if (this$httpStatusCode == null ? other$httpStatusCode != null : !this$httpStatusCode.equals(other$httpStatusCode))
            return false;
        final Object this$body = this.getBody();
        final Object other$body = other.getBody();
        if (this$body == null ? other$body != null : !this$body.equals(other$body)) return false;
        final Object this$header = this.getHeader();
        final Object other$header = other.getHeader();
        if (this$header == null ? other$header != null : !this$header.equals(other$header)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof HttpResponse;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $httpVersion = this.getHttpVersion();
        result = result * PRIME + ($httpVersion == null ? 43 : $httpVersion.hashCode());
        final Object $httpStatusCode = this.getHttpStatusCode();
        result = result * PRIME + ($httpStatusCode == null ? 43 : $httpStatusCode.hashCode());
        final Object $body = this.getBody();
        result = result * PRIME + ($body == null ? 43 : $body.hashCode());
        final Object $header = this.getHeader();
        result = result * PRIME + ($header == null ? 43 : $header.hashCode());
        return result;
    }

    public String toString() {
        return "HttpResponse(httpVersion=" + this.getHttpVersion() + ", httpStatusCode=" + this.getHttpStatusCode() + ", body=" + this.getBody() + ", header=" + this.getHeader() + ")";
    }

    public static class HttpResponseBuilder {
        private HttpVersion httpVersion;
        private HttpStatusCode httpStatusCode;
        private String body;
        private Map<String, String> header;

        HttpResponseBuilder() {
        }

        public HttpResponseBuilder httpVersion(HttpVersion httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public HttpResponseBuilder httpStatusCode(HttpStatusCode httpStatusCode) {
            this.httpStatusCode = httpStatusCode;
            return this;
        }

        public HttpResponseBuilder body(String body) {
            this.body = body;
            return this;
        }

        public HttpResponseBuilder header(Map<String, String> header) {
            this.header = header;
            return this;
        }

        public HttpResponse build() {
            return new HttpResponse(this.httpVersion, this.httpStatusCode, this.body, this.header);
        }

        public String toString() {
            return "HttpResponse.HttpResponseBuilder(httpVersion=" + this.httpVersion + ", httpStatusCode=" + this.httpStatusCode + ", body=" + this.body + ", header=" + this.header + ")";
        }
    }
}
