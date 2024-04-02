package dev.aronba;

import dev.aronba.server.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) throws IOException {

        HttpServer httpServer = new HttpServer(new InetSocketAddress(8000));
        httpServer.start();


    }
}