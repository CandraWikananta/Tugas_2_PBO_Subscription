package org.example.controller;

import org.example.models.Items;
import org.example.server.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsController {
    public List<Items> getAllItems() {
        List<Items> items = new ArrayList<>(); // Mengubah nama variabel ke itemsList
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Items";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Items item = new Items(); // Mengubah nama variabel ke item
                item.setId(rs.getInt("id"));
                item.setName(rs.getString("name"));
                item.setPrice(rs.getInt("price"));
                item.setType(rs.getString("type"));
                item.setIs_active(rs.getInt("is_active"));
                items.add(item); // Menambahkan item ke itemsList
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }


    public boolean addItems(Items items) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Items (id, name, price, type, is_active) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, items.getId());
            pstmt.setString(2, items.getName());
            pstmt.setInt(3, items.getPrice());
            pstmt.setString(4, items.getType());
            pstmt.setInt(5, items.getIs_active());
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Items getItemsById(int id) {
        Items items = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Items WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                items = new Items();
                items.setId(rs.getInt("id"));
                items.setName(rs.getString("name"));
                items.setPrice(rs.getInt("price"));
                items.setType(rs.getString("type"));
                items.setIs_active(rs.getInt("is_active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public boolean deleteItems(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Items WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean postItems(Items items) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Items (id, name, price, type, is_active) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, items.getId());
            pstmt.setString(2, items.getName());
            pstmt.setInt(3, items.getPrice());
            pstmt.setString(4, items.getType());
            pstmt.setInt(5, items.getIs_active());
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}