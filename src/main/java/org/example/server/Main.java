package org.example.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    private static final String API_KEY = "cancan";
    public static void main(String[] args)  {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost",9065), 0);
            server.createContext("/Customer", new CustomerHandler(API_KEY));
            server.createContext("/Shipping_addresses", new ShippingAddressesHandler(API_KEY));
            server.createContext("/Subscriptions", new SubscriptionHandler(API_KEY));
            server.createContext("/Cards", new CardsHandler(API_KEY));
            server.createContext("/Items", new ItemsHandler(API_KEY));

            server.setExecutor(null); // creates a default executor
            server.start();
            System.out.println("Server Started on Port 9065");
        } catch (IOException e){
            System.out.println("Server failed to start: " + e.getMessage());
        }
    }
}