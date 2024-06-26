package org.example.models;

public class Shipping_addresses {
    private int id;
    private int customer;
    private String title;
    private String line1;
    private String line2;
    private String city;
    private String province;
    private String postcode;

    public Shipping_addresses(String postcode, String province, String city, String line2, String line1, String title, int customer, int id) {
        this.postcode = postcode;
        this.province = province;
        this.city = city;
        this.line2 = line2;
        this.line1 = line1;
        this.title = title;
        this.customer = customer;
        this.id = id;
    }

    public Shipping_addresses(){

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
