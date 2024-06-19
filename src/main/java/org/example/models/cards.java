package org.example.models;

public class cards {
    private int id;
    private int customer;
    private String card_type;
    private String masked_number;
    private int expiry_month;
    private int expiry_year;
    private String status;
    private int is_primary;

    public cards(int id, int customer, String card_type, String masked_number, int expiry_month, int expiry_year, String status, int is_primary) {
        this.id = id;
        this.customer = customer;
        this.card_type = card_type;
        this.masked_number = masked_number;
        this.expiry_month = expiry_month;
        this.expiry_year = expiry_year;
        this.status = status;
        this.is_primary = is_primary;
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

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getMasked_number() {
        return masked_number;
    }

    public void setMasked_number(String masked_number) {
        this.masked_number = masked_number;
    }

    public int getExpiry_month() {
        return expiry_month;
    }

    public void setExpiry_month(int expiry_month) {
        this.expiry_month = expiry_month;
    }

    public int getExpiry_year() {
        return expiry_year;
    }

    public void setExpiry_year(int expiry_year) {
        this.expiry_year = expiry_year;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIs_primary() {
        return is_primary;
    }

    public void setIs_primary(int is_primary) {
        this.is_primary = is_primary;
    }
}
