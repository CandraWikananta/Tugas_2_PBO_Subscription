package org.example.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.example.controller.CardsController;
import org.example.models.Cards;
import com.google.gson.Gson;
import org.example.models.Subscriptions;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class CardsHandler implements HttpHandler{
    private final String apiKey;

    private final CardsController cardsController = new CardsController();

    public CardsHandler(String apiKey) {
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
            handleGetCardsRequest(t);
        } else {
            t.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGetCardsRequest(HttpExchange t) throws IOException {
        List<Cards> cards = cardsController.getAllCards();
        String json = new Gson().toJson(cards);
        t.sendResponseHeaders(200, json.length());
        OutputStream os = t.getResponseBody();
        os.write(json.getBytes());
        os.close();
    }
}
