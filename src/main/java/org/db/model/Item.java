package org.db.model;

public class Item {
    private final String title;
    private final String description;
    private final String category;
    private final double price;

    public Item(String title, String description, String category, double price) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}