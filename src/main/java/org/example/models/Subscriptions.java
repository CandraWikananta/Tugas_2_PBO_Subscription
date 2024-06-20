package org.example.models;

public class Subscriptions {
    private int id;
    private int customer;
    private int billing_period;
    private String billing_period_unit;
    private int total_due;
    private String activated_at;
    private String current_term_start;
    private String current_term_end;
    private String status;
    private Subscription_items subscriptionItemsDetails;
    private Customer customerDetails;
    private Items itemDetails;

    public Subscriptions(int id, int customer, int billing_period, String billing_period_unit, int total_due, String activated_at, String current_term_start, String current_term_end, String status) {
        this.id = id;
        this.customer = customer;
        this.billing_period = billing_period;
        this.billing_period_unit = billing_period_unit;
        this.total_due = total_due;
        this.activated_at = activated_at;
        this.current_term_start = current_term_start;
        this.current_term_end = current_term_end;
        this.status = status;
    }

    public Subscriptions() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomer() {
        return customer;
    }

    public void setCustomer(int customer) {
        this.customer = customer;
    }

    public int getBilling_period() {
        return billing_period;
    }

    public void setBilling_period(int billing_period) {
        this.billing_period = billing_period;
    }

    public String getBilling_period_unit() {
        return billing_period_unit;
    }

    public void setBilling_period_unit(String billing_period_unit) {
        this.billing_period_unit = billing_period_unit;
    }

    public int getTotal_due() {
        return total_due;
    }

    public void setTotal_due(int total_due) {
        this.total_due = total_due;
    }

    public String getActivated_at() {
        return activated_at;
    }

    public void setActivated_at(String activated_at) {
        this.activated_at = activated_at;
    }

    public String getCurrent_term_start() {
        return current_term_start;
    }

    public void setCurrent_term_start(String current_term_star) {
        this.current_term_start = current_term_star;
    }

    public String getCurrent_term_end() {
        return current_term_end;
    }

    public void setCurrent_term_end(String current_term_end) {
        this.current_term_end = current_term_end;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Subscription_items getSubscriptionItemsDetails() {
        return subscriptionItemsDetails;
    }

    public void setSubscriptionItemsDetails(Subscription_items subscriptionItems) {
        this.subscriptionItemsDetails = subscriptionItems;
    }

    public void setCustomer(Customer customer) {
    }

    public Customer getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(Customer customerDetails) {
        this.customerDetails = customerDetails;
    }

    public Items getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(Items itemDetails) {
        this.itemDetails = itemDetails;
    }
}