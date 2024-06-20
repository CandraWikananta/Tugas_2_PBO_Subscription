package org.example.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import org.example.controller.CardsController;
import org.example.models.Cards;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

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
        } else if (method.equals("DELETE")) {
            handleDeleteCardRequest(t);
        } else {
            t.sendResponseHeaders(405, -1); // Method Not Allowed
        }
    }

    private void handleGetCardsRequest(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        if (pathComponents.length == 3) {
            try {
                int cardId = Integer.parseInt(pathComponents[2]);
                Cards card = cardsController.getCardById(cardId);
                if (card != null) {
                    String json = new Gson().toJson(card);
                    t.sendResponseHeaders(200, json.length());
                    OutputStream os = t.getResponseBody();
                    os.write(json.getBytes());
                    os.close();
                } else {
                    String response = "Card dengan ID " + cardId + " tidak ditemukan";
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
        } else if (pathComponents.length == 2) {
            List<Cards> cards = cardsController.getAllCards();
            String json = new Gson().toJson(cards);
            t.sendResponseHeaders(200, json.length());
            OutputStream os = t.getResponseBody();
            os.write(json.getBytes());
            os.close();
        } else {
            String response = "Bad request";
            t.sendResponseHeaders(400, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    private void handleDeleteCardRequest(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String[] pathComponents = path.split("/");
        if (pathComponents.length == 3) {
            int id = Integer.parseInt(pathComponents[2]);
            boolean isDeleted = cardsController.deleteCard(id);
            String response = isDeleted ? "Card berhasil di delete" : "Card tidak dapat ditemukan";
            t.sendResponseHeaders(isDeleted ? 200 : 404, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } else {
            String response = "Bad request";
            t.sendResponseHeaders(400, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
