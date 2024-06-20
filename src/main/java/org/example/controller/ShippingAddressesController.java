package org.example.controller;

import org.example.models.Shipping_addresses;
import org.example.server.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ShippingAddressesController {
    public List<Shipping_addresses> getAllShippingAddresses() {
        List<Shipping_addresses> shippingAddresses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Shipping_addresses";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Shipping_addresses shippingAddress = new Shipping_addresses();
                shippingAddress.setId(rs.getInt("id"));
                shippingAddress.setCustomer(rs.getInt("customer"));
                shippingAddress.setTitle(rs.getString("title"));
                shippingAddress.setLine1(rs.getString("line1"));
                shippingAddress.setLine2(rs.getString("line2"));
                shippingAddress.setCity(rs.getString("city"));
                shippingAddress.setProvince(rs.getString("province"));
                shippingAddress.setPostcode(rs.getString("postcode"));
                shippingAddresses.add(shippingAddress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingAddresses;
    }

    public Shipping_addresses getShippingAddressById(int id) {
        Shipping_addresses shippingAddress = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Shipping_addresses WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                shippingAddress = new Shipping_addresses();
                shippingAddress.setId(rs.getInt("id"));
                shippingAddress.setCustomer(rs.getInt("customer"));
                shippingAddress.setTitle(rs.getString("title"));
                shippingAddress.setLine1(rs.getString("line1"));
                shippingAddress.setLine2(rs.getString("line2"));
                shippingAddress.setCity(rs.getString("city"));
                shippingAddress.setProvince(rs.getString("province"));
                shippingAddress.setPostcode(rs.getString("postcode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingAddress;
    }

    public boolean updateShippingAddressById(int id, Shipping_addresses address) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Shipping_addresses SET title = ?, line1 = ?, line2 = ?, city = ?, province = ?, postcode = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, address.getTitle());
            pstmt.setString(2, address.getLine1());
            pstmt.setString(3, address.getLine2());
            pstmt.setString(4, address.getCity());
            pstmt.setString(5, address.getProvince());
            pstmt.setString(6, address.getPostcode());
            pstmt.setInt(7, id);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
