package org.example.controller;

import org.example.models.*;
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
                subs.setCurrent_term_end(rs.getString("current_term_end"));
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
            pstmt.setString(8, subscriptions.getCurrent_term_end());
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
                subs.setCurrent_term_end(rs.getString("current_term_end"));
                subs.setStatus(rs.getString("status"));
                subscriptions.add(subs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    public List<Subscriptions> getSubscriptionsByCustomerIdAndStatus(int customerId, String status) {
        List<Subscriptions> subscriptions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Subscriptions WHERE customer = ? AND status = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            pstmt.setString(2, status);
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
                subs.setCurrent_term_end(rs.getString("current_term_end"));
                subs.setStatus(rs.getString("status"));
                subscriptions.add(subs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscriptions;
    }

    public Subscriptions getSubscriptionById(int subscriptionId) {
        Subscriptions subscription = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT s.*, c.id as customer_id, c.first_name, c.last_name, c.email, c.phone_number, " +
                    "si.quantity, si.amount, i.id as item_id, i.name as item_name, i.price as item_price, i.type as item_type " +
                    "FROM Subscriptions s " +
                    "JOIN Customer c ON s.customer = c.id " +
                    "JOIN Subscription_items si ON s.id = si.subscription " +
                    "JOIN Items i ON si.item = i.id " +
                    "WHERE s.id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, subscriptionId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                if (subscription == null) {
                    subscription = new Subscriptions();
                    subscription.setId(rs.getInt("id"));
                    subscription.setBilling_period(rs.getInt("billing_period"));
                    subscription.setBilling_period_unit(rs.getString("billing_period_unit"));
                    subscription.setTotal_due(rs.getInt("total_due"));
                    subscription.setActivated_at(rs.getString("activated_at"));
                    subscription.setCurrent_term_start(rs.getString("current_term_start"));
                    subscription.setCurrent_term_end(rs.getString("current_term_end"));
                    subscription.setStatus(rs.getString("status"));

                    Customer customer = new Customer();
                    customer.setId(rs.getInt("customer_id"));
                    customer.setFirst_name(rs.getString("first_name"));
                    customer.setLast_name(rs.getString("last_name"));
                    subscription.setCustomer(customer.getId());
                    subscription.setCustomerDetails(customer);
                }

                Subscription_items subscriptionItem = new Subscription_items();
                subscriptionItem.setQuantity(rs.getInt("quantity"));
                subscriptionItem.setAmount(rs.getInt("amount"));
                subscription.setSubscriptionItems(subscriptionItem);

                Items item = new Items();
                item.setId(rs.getInt("item_id"));
                item.setName(rs.getString("item_name"));
                item.setPrice(rs.getInt("item_price"));
                item.setType(rs.getString("item_type"));
                subscriptionItem.setItemDetails(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subscription;
    }
}
