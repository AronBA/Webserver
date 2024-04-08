package dev.aronba;

import dev.aronba.server.HttpServer;
import dev.aronba.server.HttpServerConfigReader;

import javax.imageio.IIOException;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {


    //used for testing
    public static HttpServer httpServer;

    public static void main(String[] args) throws IOException {

        if (args.length != 2 || !args[0].equals("-f")) {
            System.out.println("Usage: java -jar webserver.jar -f configFilePath");
            throw new IllegalArgumentException("Incorrect Argument");
        }
        HttpServerConfigReader httpServerConfigReader = new HttpServerConfigReader(args[1]);
        try {
            httpServerConfigReader.loadConfig();
        } catch (IOException e) {
            throw new IIOException("Could not load config");
        }

        InetSocketAddress inetSocketAddress = new InetSocketAddress(HttpServerConfigReader.PORT);


        httpServer = new HttpServer(inetSocketAddress, HttpServerConfigReader.DEVELOPER_MODE);
        httpServer.start();


    }
}
