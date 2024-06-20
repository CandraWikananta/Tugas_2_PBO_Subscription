package org.example.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.example.controller.CustomerController;
import org.example.models.Customer;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

public class CustomerHandler implements HttpHandler {
    private final String apiKey;
    private final CustomerController customerController = new CustomerController();

    public CustomerHandler(String apiKey) {
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
            handleGetCustomerRequest(t);
        } else if (method.equals("POST")) {
            handlePostCustomerRequest(t);
        } else {
            t.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }



    private void handleGetCustomerRequest(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        if (pathComponents.length == 3) { // Expecting /Customer/{id}
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
                    String response = "Customer not found";
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
            List<Customer> customers = customerController.getAllCustomers();
            String json = new Gson().toJson(customers);
            t.sendResponseHeaders(200, json.length());
            OutputStream os = t.getResponseBody();
            os.write(json.getBytes());
            os.close();
        }
    }

    private void handlePostCustomerRequest(HttpExchange t) throws IOException {
        InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
        Customer customer = new Gson().fromJson(isr, Customer.class);
        boolean success = customerController.postCustomer(customer);
        String response = success ? "Error creating customer" : "Customer created successfully";
        t.sendResponseHeaders(success ? 500 : 201, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
