package org.example.models;

public class items {
    private int id;
    private String name;
    private int price;
    private String type;
    private int is_active;

    public items(int id, String name, int price, String type, int is_active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.is_active = is_active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }
}


