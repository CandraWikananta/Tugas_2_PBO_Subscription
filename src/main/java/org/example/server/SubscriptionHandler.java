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
public class SubscriptionHandler implements HttpHandler{
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
        String path = t.getRequestURI().getPath();
        if (method.equals("GET")) {
            handleGetSubscriptionRequest(t);
        } else {
            t.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGetSubscriptionRequest(HttpExchange t) throws IOException{
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        List<Subscriptions> subscriptions = subscriptionController.getAllSubscription();
        String json = new Gson().toJson(subscriptions);
        t.sendResponseHeaders(200, json.length());
        OutputStream os = t.getResponseBody();
        os.write(json.getBytes());
        os.close();

    }
}
