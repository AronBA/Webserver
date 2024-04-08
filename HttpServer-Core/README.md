# HttpServer-Core
Library containing the code of the Webserver.



## ConfigReader
The Server needs a  config.yml as well as at least one call to the HttpServerConfigReader before starting up.

```java

import dev.aronba.server.HttpServerConfigReader;

public void startUp() {

    HttpServerConfigReader httpServerConfigReader = new HttpServerConfigReader("url/to/config");
    httpServerConfigReader.loadConfig();
    
    // start server below
    

}

```


## RequestHandlers & Mappings
Every request which is sent to the Server is handled by a RequestHandler.  
You can overwrite the standard implementation for specific URLs by implementing the RequestHandler interface.

```java

import dev.aronba.server.HttpServer;
import dev.aronba.server.http.HttpMethod;
import dev.aronba.server.http.HttpResponse;
import dev.aronba.server.requestHandler.RequestHandler;

import java.net.InetSocketAddress;

public void foo() {

    InetSocketAddress inetSocketAddress = new InetSocketAddress(8080);
    HttpServer httpServer = new HttpServer(inetSocketAddress);

    RequestHandler userApi = (HttpRequest) -> {
        //do some stuff here like reading all the users out of a database
        return HttpResponse.builder().build();
    };

    //add handler to a specific endpoint
    httpServer.addRequestMapping(HttpMethod.GET,"/users",userApi);

}
```