package dev.aronba.server.http;


public enum HttpCacheControl {

    NO_CACHE("no-cache"),
    NO_STORE("no-store"),
    MAX_AGE("max-age"),
    MAX_STALE("max-stale"),
    MIN_FRESH("min-fresh"),
    NO_TRANSFORM("no-transform"),
    ONLY_IF_CACHED("only-if-cached"),
    PUBLIC("public"),
    PRIVATE("private"),
    NO_CACHE_NO_STORE("no-cache, no-store"),
    NO_CACHE_MAX_AGE("no-cache, max-age"),
    NO_CACHE_MAX_STALE("no-cache, max-stale"),
    NO_CACHE_MIN_FRESH("no-cache, min-fresh"),
    NO_CACHE_ONLY_IF_CACHED("no-cache, only-if-cached");

    private final String directive;

    private HttpCacheControl(String directive) {
        this.directive = directive;
    }

    public String getDirective() {
        return this.directive;
    }

    public String toString() {
        return "HttpCacheControl(directive=" + this.getDirective() + ")";
    }
}
