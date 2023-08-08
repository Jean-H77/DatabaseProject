package org.db.model;

import java.util.List;

public class Item {
    private final String title;
    private final String description;
    private final double price;
    private final List<String> categories;
    private final List<String> type;
    private final List<String> maker;


    public Item(String title, String description, double price, List<String> categories, List<String> type, List<String> maker) {
        this.title = title;
        this.description = description;
        this.categories = categories;
        this.price = price;
        this.type = type;
        this.maker = maker;
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<String> getType() {
        return type;
    }

    public List<String> getMaker() {
        return maker;
    }


}
