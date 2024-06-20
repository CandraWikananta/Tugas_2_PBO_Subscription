package org.example.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.example.controller.ItemsController;
import org.example.models.Items;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class ItemsHandler implements HttpHandler {
    private final String apiKey;
    private final ItemsController itemsController = new ItemsController();

    public ItemsHandler(String apiKey) {
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
            handleGetItemsRequest(t);
        } else if (method.equals("POST")) {
            handlePostItemsRequest(t);
        } else if (method.equals("DELETE")) {
            handleDeleteRequest(t);
        } else {
            t.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGetItemsRequest(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        if (pathComponents.length == 3) { // Expecting /Items/{id}
            try {
                int id = Integer.parseInt(pathComponents[2]);
                Items items = itemsController.getItemsById(id);
                if (items != null) {
                    String json = new Gson().toJson(items);
                    t.sendResponseHeaders(200, json.length());
                    OutputStream os = t.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                } else {
                    String response = "Items not found";
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
        } else {
            List<Items> items = itemsController.getAllItems();
            String json = new Gson().toJson(items);
            t.sendResponseHeaders(200, json.length());
            OutputStream os = t.getResponseBody();
            os.write(json.getBytes());
            os.close();
        }
    }

    private void handlePostItemsRequest(HttpExchange t) throws IOException {
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        Items items = new Gson().fromJson(isr, Items.class);
        boolean success = itemsController.postItems(items);
        String response = success ? "Items created successfully" : "Error creating items";
        t.sendResponseHeaders(success ? 201 : 500, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private void handleDeleteRequest(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        if (pathComponents.length == 3) {
            try {
                int id = Integer.parseInt(pathComponents[2]);
                boolean isDeleted = itemsController.deleteItems(id);
                String response = isDeleted ? "Items deleted successfully" : "Items not found";
                t.sendResponseHeaders(isDeleted ? 200 : 404, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } catch (NumberFormatException e) {
                String response = "Invalid ID format";
                t.sendResponseHeaders(400, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else {
            String response = "Bad request";
            t.sendResponseHeaders(400, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}