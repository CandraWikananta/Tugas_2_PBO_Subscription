package org.example.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.example.controller.SubscriptionController;
import org.example.models.Subscriptions;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class SubscriptionHandler implements HttpHandler {
    private final String apiKey;
    private final SubscriptionController subscriptionController = new SubscriptionController();

    public SubscriptionHandler(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void handle(HttpExchange t) throws IOException {
        if (!t.getRequestHeaders().containsKey("API-Key") || !t.getRequestHeaders().getFirst("API-Key").equals(apiKey)) {
            String response = "Unauthorized";
            t.sendResponseHeaders(401, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
            return;
        }
        String method = t.getRequestMethod();
        if (method.equals("GET")) {
            handleGetSubscriptionRequest(t);
        } else if (method.equals("POST")) {
            handlePostSubscriptionRequest(t);
        } else {
            t.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGetSubscriptionRequest(HttpExchange t) throws IOException {
        String query = t.getRequestURI().getQuery();
        Map<String, String> params = queryToMap(query);
        String sortBy = params.get("sort_by");
        String sortType = params.get("sort_type");

        List<Subscriptions> subscriptions = subscriptionController.getAllSubscription(sortBy, sortType);
        String json = new Gson().toJson(subscriptions);
        t.sendResponseHeaders(200, json.length());
        OutputStream os = t.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }

    private void handlePostSubscriptionRequest(HttpExchange t) throws IOException {
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        Subscriptions subscriptions = new Gson().fromJson(isr, Subscriptions.class);
        boolean success = subscriptionController.postSubscriptions(subscriptions);
        String response = success ? "Error creating Subscription" : "Subscription created successfully";
        t.sendResponseHeaders(success ? 500 : 201, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map<String, String> queryToMap(String query) {
        Map<String, String> result = new java.util.HashMap<>();
        if (query == null) return result;
        for (String param : query.split("&")) {
            String[] entry = param.split("=");
            if (entry.length > 1) {
                result.put(entry[0], entry[1]);
            } else {
                result.put(entry[0], "");
            }
        }
        return result;
    }
}
