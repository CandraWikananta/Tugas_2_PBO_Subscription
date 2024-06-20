package org.example.controller;

import org.example.models.Subscriptions;
import org.example.server.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class SubscriptionController {
    public List<Subscriptions> getAllSubscription(String sortBy, String sortType) {
        List<Subscriptions> subscriptions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Subscriptions";
            if (sortBy != null && sortType != null) {
                sql += " ORDER BY " + sortBy + " " + sortType;
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Subscriptions subs = new Subscriptions();
                subs.setId(rs.getInt("id"));
                subs.setCustomer(rs.getInt("customer"));
                subs.setBilling_period(rs.getInt("billing_period"));
                subs.setBilling_period_unit(rs.getString("billing_period_unit"));
                subs.setTotal_due(rs.getInt("total_due"));
                subs.setActivated_at(rs.getString("activated_at"));
                subs.setCurrent_term_start(rs.getString("current_term_start"));
                subs.setGetCurrent_term_end(rs.getString("current_term_end"));
                subs.setStatus(rs.getString("status"));
                subscriptions.add(subs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    public boolean postSubscriptions(Subscriptions subscriptions) {
        String response;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Subscriptions (id, customer, billing_period, billing_period_unit, total_due, activated_at, current_term_start, current_term_end, status)VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, subscriptions.getId());
            pstmt.setInt(2, subscriptions.getCustomer());
            pstmt.setInt(3, subscriptions.getBilling_period());
            pstmt.setString(4, subscriptions.getBilling_period_unit());
            pstmt.setInt(5, subscriptions.getTotal_due());
            pstmt.setString(6, subscriptions.getActivated_at());
            pstmt.setString(7, subscriptions.getCurrent_term_start());
            pstmt.setString(8, subscriptions.getGetCurrent_term_end());
            pstmt.setString(9, subscriptions.getStatus());
            int rowsInserted = pstmt.executeUpdate();
            if(rowsInserted > 0){
                response = rowsInserted + "row(s) has been inserted";
                System.out.println(response);
            }else{
                response = "no rows have been inserted";
                System.out.println(response);
            }
//            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Subscriptions> getSubscriptionsByCustomerId(int customerId) {
        List<Subscriptions> subscriptions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Subscriptions WHERE customer = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Subscriptions subs = new Subscriptions();
                subs.setId(rs.getInt("id"));
                subs.setCustomer(rs.getInt("customer"));
                subs.setBilling_period(rs.getInt("billing_period"));
                subs.setBilling_period_unit(rs.getString("billing_period_unit"));
                subs.setTotal_due(rs.getInt("total_due"));
                subs.setActivated_at(rs.getString("activated_at"));
                subs.setCurrent_term_start(rs.getString("current_term_start"));
                subs.setGetCurrent_term_end(rs.getString("current_term_end"));
                subs.setStatus(rs.getString("status"));
                subscriptions.add(subs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }
}
