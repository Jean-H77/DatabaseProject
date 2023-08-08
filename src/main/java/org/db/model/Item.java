package org.db.model;

public class Item {
    private final String title;
    private final String description;
    private final double price;
    private final String category;
    private final String type;
    private final String maker;


    public Item(String title, String description, double price, String categories, String type, String maker) {
        this.title = title;
        this.description = description;
        this.category = categories;
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

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getMaker() {
        return maker;
    }


}
