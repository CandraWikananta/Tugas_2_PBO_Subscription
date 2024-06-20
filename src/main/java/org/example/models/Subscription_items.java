package org.example.models;

public class Subscription_items {
    private int subscription;
    private int item;
    private int quantity;
    private int price;
    private int amount;

    private Items itemDetails;

    public Subscription_items(int amount, int price, int quantity, int item, int subscription) {
        this.amount = amount;
        this.price = price;
        this.quantity = quantity;
        this.item = item;
        this.subscription = subscription;
    }

    public Subscription_items(){

    }

    public int getSubscription() {
        return subscription;
    }

    public void setSubscription(int subscription) {
        this.subscription = subscription;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Items getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(Items itemDetails) {
        this.itemDetails = itemDetails;
    }
}
