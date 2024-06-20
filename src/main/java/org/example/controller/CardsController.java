package org.example.controller;

import org.example.models.Cards;
import org.example.server.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardsController {
    public List<Cards> getAllCards() {
        List<Cards> cards = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Cards";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Cards card = new Cards();
                card.setId(rs.getInt("id"));
                card.setCustomer(rs.getInt("customer"));
                card.setCard_type(rs.getString("card_type"));
                card.setMasked_number(rs.getString("masked_number"));
                card.setExpiry_month(rs.getInt("expiry_month"));
                card.setExpiry_year(rs.getInt("expiry_year"));
                card.setStatus(rs.getString("status"));
                card.setIs_primary(rs.getInt("is_primary"));
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public List<Cards> getCardsByCustomerId(int customerId) {
        List<Cards> cards = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Cards WHERE customer = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Cards card = new Cards();
                card.setId(rs.getInt("id"));
                card.setCustomer(rs.getInt("customer"));
                card.setCard_type(rs.getString("card_type"));
                card.setMasked_number(rs.getString("masked_number"));
                card.setExpiry_month(rs.getInt("expiry_month"));
                card.setExpiry_year(rs.getInt("expiry_year"));
                card.setStatus(rs.getString("status"));
                card.setIs_primary(rs.getInt("is_primary"));
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public Cards getCardById(int cardId) {
        Cards card = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Cards WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cardId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                card = new Cards();
                card.setId(rs.getInt("id"));
                card.setCustomer(rs.getInt("customer"));
                card.setCard_type(rs.getString("card_type"));
                card.setMasked_number(rs.getString("masked_number"));
                card.setExpiry_month(rs.getInt("expiry_month"));
                card.setExpiry_year(rs.getInt("expiry_year"));
                card.setStatus(rs.getString("status"));
                card.setIs_primary(rs.getInt("is_primary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return card;
    }

    public boolean deleteCard(int cardId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Cards WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cardId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
