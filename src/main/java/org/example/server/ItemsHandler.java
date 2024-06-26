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
        } else if (method.equals("PUT")) {
            handlePutItemsRequest(t);
        } else {
            t.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGetItemsRequest(HttpExchange t) throws IOException {
        String query = t.getRequestURI().getQuery();
        if (query != null && query.contains("is_active=true")) {
            List<Items> items = itemsController.getActiveItems();
            String json = new Gson().toJson(items);
            t.sendResponseHeaders(200, json.length());
            OutputStream os = t.getResponseBody();
            os.write(json.getBytes());
            os.close();
        } else {
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
                        String response = "Item dengan id "+ id + " tidak dapat ditemukan";
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
    }

    private void handlePostItemsRequest(HttpExchange t) throws IOException {
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        Items items = new Gson().fromJson(isr, Items.class);
        boolean success = itemsController.postItems(items);
        String response = success ? "Item berhasil ditambahkan" : "Error menambahkan Item";
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
                String response = isDeleted ? "Item berhasil di delete" : "Item tidak dapat ditemukan";
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

    private void handlePutItemsRequest(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        if (pathComponents.length == 3) {
            try {
                int id = Integer.parseInt(pathComponents[2]);
                InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                Items item = new Gson().fromJson(isr, Items.class);
                boolean success = itemsController.updateItems(id, item);
                String response = success ? "Item berhasil diperbarui" : "Item tidak ditemukan atau gagal diperbarui";
                t.sendResponseHeaders(success ? 200 : 404, response.length());
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