package org.example.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.example.controller.ShippingAddressesController;
import org.example.models.Shipping_addresses;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
public class ShippingAddressesHandler implements HttpHandler {
    private final String apiKey;
    private final ShippingAddressesController shippingAddressController = new ShippingAddressesController();

    public ShippingAddressesHandler(String apiKey) {
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
            handleGetShippingAddressRequest(t);
        } else if (method.equals("PUT")) {
            handlePutShippingAddressRequest(t);
        }else {
            t.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGetShippingAddressRequest(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        if (pathComponents.length == 3) {
            try {
                int id = Integer.parseInt(pathComponents[2]);
                Shipping_addresses shippingAddress = shippingAddressController.getShippingAddressById(id);
                if (shippingAddress != null) {
                    String json = new Gson().toJson(shippingAddress);
                    t.sendResponseHeaders(200, json.length());
                    OutputStream os = t.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                } else {
                    String response = "Shipping Address dengan id " + id + " tidak dapat ditemukan";
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
            List<Shipping_addresses> shippingAddresses = shippingAddressController.getAllShippingAddresses();
            String json = new Gson().toJson(shippingAddresses);
            t.sendResponseHeaders(200, json.length());
            OutputStream os = t.getResponseBody();
            os.write(json.getBytes());
            os.close();
        }
    }

    private void handlePutShippingAddressRequest(HttpExchange t) throws IOException{
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        if (pathComponents.length == 3) {
            try {
                int id = Integer.parseInt(pathComponents[2]);
                InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
                Shipping_addresses shipAdd = new Gson().fromJson(isr, Shipping_addresses.class);
                boolean success = shippingAddressController.updateShippingAddressById(id, shipAdd);
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
