package org.example.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            handleGetRequest(exchange);
        }
    }

    private void handleGetRequest(HttpExchange exchange) throws IOException {
        Connection conn = DatabaseConnection.connect();
        String response = "";
        try (Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM customers";
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            while (rs.next()) {
                sb.append("ID: ").append(rs.getInt("id")).append(", ");
                sb.append("Name: ").append(rs.getString("first_name")).append(" ");
                sb.append(rs.getString("last_name")).append("\n");
            }
            response = sb.toString();
        } catch (SQLException e) {
            response = "Error: " + e.getMessage();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
