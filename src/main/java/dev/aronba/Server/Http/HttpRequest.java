package dev.aronba;

import dev.aronba.constant.HttpMethod;
import dev.aronba.constant.HttpStatusCode;

import java.net.URL;
import java.util.Map;

public class HttpRequest {
    HttpStatusCode httpStatusCode;
    HttpMethod httpMethod;
    URL url;
    Map<String,String> header;
    String body;
}
