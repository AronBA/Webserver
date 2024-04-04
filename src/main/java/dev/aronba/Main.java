package dev.aronba;

import dev.aronba.server.HttpServer;
import dev.aronba.server.ServerConfigReader;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length != 2 || !args[0].equals("-f")) {
            System.out.println("Usage: java -jar webserver.jar -f configFilePath");
            return;
        }
        ServerConfigReader serverConfigReader = new ServerConfigReader(args[1]);
        try {
            serverConfigReader.loadConfig();
        } catch (IOException e) {
            return;
        }

        InetSocketAddress inetSocketAddress = new InetSocketAddress(ServerConfigReader.PORT);

        HttpServer httpServer = new HttpServer(inetSocketAddress);
        httpServer.start();


    }
}