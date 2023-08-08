package org.db.model;

import java.util.List;

public class Item {
    private final String title;
    private final String description;
    private final List<String> categories;
    private final double price;

    public Item(String title, String description, List<String> categories, double price) {
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getCategories() {
        return categories;
    }

    public double getPrice() {
        return price;
    }
}
