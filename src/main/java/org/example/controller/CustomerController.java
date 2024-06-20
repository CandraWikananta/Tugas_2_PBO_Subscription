package org.example.controller;

import org.example.models.Customer;
import org.example.models.Shipping_addresses;
import org.example.server.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Customer";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomerById(int id) {
        Customer customer = null;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT c.id, c.email, c.first_name, c.last_name, c.phone_number, s.line1 " +
                    "FROM Customer c " +
                    "LEFT JOIN Shipping_addresses s ON c.id = s.customer " +
                    "WHERE c.id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone_number(rs.getString("phone_number"));
                customer.setShippingAddresses(getShippingAddressesByCustomerId(id));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public boolean deleteCustomer(int id) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM Customer WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean postCustomer(Customer customer) {
        String response;
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO Customer (id, email, first_name, last_name, phone_number)VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customer.getId());
            pstmt.setString(2, customer.getEmail());
            pstmt.setString(3, customer.getFirst_name());
            pstmt.setString(4, customer.getLast_name());
            pstmt.setString(5, customer.getPhone_number());
            int rowsInserted = pstmt.executeUpdate();
            if(rowsInserted > 0){
                response = rowsInserted + "row(s) has been inserted";
                System.out.println(response);
            }else{
                response = "no rows have been inserted";
                System.out.println(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCustomer(int id, Customer customer) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "UPDATE Customer SET email = ?, first_name = ?, last_name = ?, phone_number = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, customer.getEmail());
            pstmt.setString(2, customer.getFirst_name());
            pstmt.setString(3, customer.getLast_name());
            pstmt.setString(4, customer.getPhone_number());
            pstmt.setInt(5, id);
            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private List<Shipping_addresses> getShippingAddressesByCustomerId(int customerId) {
        List<Shipping_addresses> addresses = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM Shipping_addresses WHERE customer = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Shipping_addresses address = new Shipping_addresses();
                address.setId(rs.getInt("id"));
                address.setCustomer(rs.getInt("customer"));
                address.setTitle(rs.getString("title"));
                address.setLine1(rs.getString("line1"));
                address.setLine2(rs.getString("line2"));
                address.setCity(rs.getString("city"));
                address.setProvince(rs.getString("province"));
                address.setPostcode(rs.getString("postcode"));
                addresses.add(address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addresses;
    }
}
