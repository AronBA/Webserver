package dev.aronba.server.http;

import java.util.Map;


public class HttpRequest {
    HttpMethod httpMethod;
    String httpVersion;
    String requestUrl;
    Map<String, String> header;
    String body;

    public HttpRequest(HttpMethod httpMethod, String httpVersion, String requestUrl, Map<String, String> header, String body) {
        this.httpMethod = httpMethod;
        this.httpVersion = httpVersion;
        this.requestUrl = requestUrl;
        this.header = header;
        this.body = body;
    }

    public HttpRequest() {
    }

    public static HttpRequestBuilder builder() {
        return new HttpRequestBuilder();
    }

    public HttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public String getHttpVersion() {
        return this.httpVersion;
    }

    public String getRequestUrl() {
        return this.requestUrl;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public String getBody() {
        return this.body;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void setHeader(Map<String, String> header) {
        this.header = header;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof HttpRequest)) return false;
        final HttpRequest other = (HttpRequest) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$httpMethod = this.getHttpMethod();
        final Object other$httpMethod = other.getHttpMethod();
        if (this$httpMethod == null ? other$httpMethod != null : !this$httpMethod.equals(other$httpMethod))
            return false;
        final Object this$httpVersion = this.getHttpVersion();
        final Object other$httpVersion = other.getHttpVersion();
        if (this$httpVersion == null ? other$httpVersion != null : !this$httpVersion.equals(other$httpVersion))
            return false;
        final Object this$requestUrl = this.getRequestUrl();
        final Object other$requestUrl = other.getRequestUrl();
        if (this$requestUrl == null ? other$requestUrl != null : !this$requestUrl.equals(other$requestUrl))
            return false;
        final Object this$header = this.getHeader();
        final Object other$header = other.getHeader();
        if (this$header == null ? other$header != null : !this$header.equals(other$header)) return false;
        final Object this$body = this.getBody();
        final Object other$body = other.getBody();
        if (this$body == null ? other$body != null : !this$body.equals(other$body)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof HttpRequest;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $httpMethod = this.getHttpMethod();
        result = result * PRIME + ($httpMethod == null ? 43 : $httpMethod.hashCode());
        final Object $httpVersion = this.getHttpVersion();
        result = result * PRIME + ($httpVersion == null ? 43 : $httpVersion.hashCode());
        final Object $requestUrl = this.getRequestUrl();
        result = result * PRIME + ($requestUrl == null ? 43 : $requestUrl.hashCode());
        final Object $header = this.getHeader();
        result = result * PRIME + ($header == null ? 43 : $header.hashCode());
        final Object $body = this.getBody();
        result = result * PRIME + ($body == null ? 43 : $body.hashCode());
        return result;
    }

    public String toString() {
        return "HttpRequest(httpMethod=" + this.getHttpMethod() + ", httpVersion=" + this.getHttpVersion() + ", requestUrl=" + this.getRequestUrl() + ", header=" + this.getHeader() + ", body=" + this.getBody() + ")";
    }

    public static class HttpRequestBuilder {
        private HttpMethod httpMethod;
        private String httpVersion;
        private String requestUrl;
        private Map<String, String> header;
        private String body;

        HttpRequestBuilder() {
        }

        public HttpRequestBuilder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public HttpRequestBuilder httpVersion(String httpVersion) {
            this.httpVersion = httpVersion;
            return this;
        }

        public HttpRequestBuilder requestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        public HttpRequestBuilder header(Map<String, String> header) {
            this.header = header;
            return this;
        }

        public HttpRequestBuilder body(String body) {
            this.body = body;
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(this.httpMethod, this.httpVersion, this.requestUrl, this.header, this.body);
        }

        public String toString() {
            return "HttpRequest.HttpRequestBuilder(httpMethod=" + this.httpMethod + ", httpVersion=" + this.httpVersion + ", requestUrl=" + this.requestUrl + ", header=" + this.header + ", body=" + this.body + ")";
        }
    }
}
