package dev.aronba;

import dev.aronba.server.HttpServer;
import dev.aronba.server.HttpServerConfigReader;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {

        if (args.length != 2 || !args[0].equals("-f")) {
            System.out.println("Usage: java -jar webserver.jar -f configFilePath");
            return;
        }
        HttpServerConfigReader httpServerConfigReader = new HttpServerConfigReader(args[1]);
        try {
            httpServerConfigReader.loadConfig();
        } catch (IOException e) {
            return;
        }

        InetSocketAddress inetSocketAddress = new InetSocketAddress(HttpServerConfigReader.PORT);


        HttpServer httpServer = new HttpServer(inetSocketAddress, HttpServerConfigReader.DEVELOPER_MODE);
        httpServer.start();


    }
}
