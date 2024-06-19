package org.example.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args)  {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(9065), 0);
            server.createContext("/models/Customer", new CustomerHandler());

            server.setExecutor(null);

            server.start();
            System.out.println("Server Started on port 9065");

        } catch (IOException e){
            System.err.println("Server failed to start: " + e.getMessage());        }

    }
}