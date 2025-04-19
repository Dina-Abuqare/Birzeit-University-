package com.example.assignment1;

public class Product {
    private String name;
    private String category;
    private int quantity;
    private int price;


    public Product(String name, String category, int quantity, int price) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter and  Setters
    public String getName() {
        return name;
    }


    public String getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }


    public int getPrice() {
        return price;
    }
}
