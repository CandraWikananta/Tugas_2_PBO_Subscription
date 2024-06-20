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
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");

        if (pathComponents.length == 3) { // Expecting /subscriptions/{id}
            try {
                int id = Integer.parseInt(pathComponents[2]);
                Subscriptions subscription = subscriptionController.getSubscriptionById(id);
                if (subscription != null) {
                    String json = new Gson().toJson(subscription);
                    t.sendResponseHeaders(200, json.length());
                    OutputStream os = t.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                } else {
                    String response = "Subscription dengan id "+ id +" tidak dapat ditemukan";
                    t.sendResponseHeaders(404, response.length());
                    OutputStream os = t.getResponseBody();
                    os.write(response.getBytes());
                    os.close();
                }
            } catch (NumberFormatException e) {
                String response = "Invalid ID format";
                t.sendResponseHeaders(400, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else if (pathComponents.length == 2) { // Expecting /subscriptions
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
        } else {
            t.sendResponseHeaders(404, -1); // Not Found
        }
    }

    // POST Subscriptions
    private void handlePostSubscriptionRequest(HttpExchange t) throws IOException {
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        Subscriptions subscriptions = new Gson().fromJson(isr, Subscriptions.class);
        boolean success = subscriptionController.postSubscriptions(subscriptions);
        String response = success ? "Error menambahkan Subscription" : "Subscription berhasil ditambahkan";
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
