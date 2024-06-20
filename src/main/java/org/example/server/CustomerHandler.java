package org.example.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.example.controller.*;
import org.example.models.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class CustomerHandler implements HttpHandler {
    private final String apiKey;
    private final CustomerController customerController = new CustomerController();
    private final SubscriptionController subscriptionController = new SubscriptionController();

    private final CardsController cardsController = new CardsController();

    public CustomerHandler(String apiKey) {
        this.apiKey = apiKey;
    }

    // Mengecek API-Key jika sesuai dengan API-Key pada Main.Java
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

        // Mengecek Metode yang digunakan (GET, POST, DELETE, PUT)
        String method = t.getRequestMethod();
        String path = t.getRequestURI().getPath();
        if (method.equals("GET")) {
            handleGetCustomerRequest(t);
        } else if (method.equals("POST")) {
            handlePostCustomerRequest(t);
        } else if (method.equals("DELETE")) {
            handleDeleteRequest(t);
        } else {
            t.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGetCustomerRequest(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        String query = t.getRequestURI().getQuery();
        if (pathComponents.length == 3) { // jika GET/Customer/{id}
            try {
                int id = Integer.parseInt(pathComponents[2]);
                Customer customer = customerController.getCustomerById(id);
                if (customer != null) {
                    String json = new Gson().toJson(customer);
                    t.sendResponseHeaders(200, json.length());
                    OutputStream os = t.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                } else {
                    String response = "Customer dengan id " + id + " tidak dapat ditemukan";
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
        } else if (pathComponents.length == 4 && "Subscriptions".equals(pathComponents[3])) { // jika GET/customers/{id}/subscriptions
            try {
                int customerId = Integer.parseInt(pathComponents[2]);
                if (query != null && query.startsWith("subscriptions_status=")) { // jika GET/customers/{id}/subscriptions?subscription_status={active, cancelled}
                    String status = query.split("=")[1];
                    List<Subscriptions> subscriptions = subscriptionController.getSubscriptionsByCustomerIdAndStatus(customerId, status);
                    String json = new Gson().toJson(subscriptions);
                    t.sendResponseHeaders(200, json.length());
                    OutputStream os = t.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                } else {
                    List<Subscriptions> subscriptions = subscriptionController.getSubscriptionsByCustomerId(customerId);
                    String json = new Gson().toJson(subscriptions);
                    t.sendResponseHeaders(200, json.length());
                    OutputStream os = t.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                }
            } catch (NumberFormatException e) {
                String response = "Invalid ID format";
                t.sendResponseHeaders(400, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        } else if (pathComponents.length == 4 && "Cards".equals(pathComponents[3])) { // Expecting /customers/{id}/cards
            try {
                int customerId = Integer.parseInt(pathComponents[2]);
                List<Cards> cards = cardsController.getCardsByCustomerId(customerId);
                String json = new Gson().toJson(cards);
                t.sendResponseHeaders(200, json.length());
                OutputStream os = t.getResponseBody();
                os.write(json.getBytes());
                os.close();
            } catch (NumberFormatException e) {
                String response = "Invalid ID format";
                t.sendResponseHeaders(400, response.length());
                OutputStream os = t.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }else { // jika GET/Customers akan mengambil semua Customers
            List<Customer> customers = customerController.getAllCustomers();
            String json = new Gson().toJson(customers);
            t.sendResponseHeaders(200, json.length());
            OutputStream os = t.getResponseBody();
            os.write(json.getBytes());
            os.close();
        }
    }

    // POST/Customer
    private void handlePostCustomerRequest(HttpExchange t) throws IOException {
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        Customer customer = new Gson().fromJson(isr, Customer.class);
        boolean success = customerController.postCustomer(customer);
        String response = success ? "Error menambahkan Customer" : "Customer berhasil ditambahkan";
        t.sendResponseHeaders(success ? 500 : 201, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    // DELETE/Customer/{id}
    private void handleDeleteRequest(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        if (pathComponents.length == 3) {
            try {
                int id = Integer.parseInt(pathComponents[2]);
                boolean isDeleted = customerController.deleteCustomer(id);
                String response = isDeleted ? "Customer berhasil di delete" : "Customer tidak dapat ditemukan";
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
