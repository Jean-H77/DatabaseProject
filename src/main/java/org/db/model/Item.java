package org.db.model;

import java.sql.Timestamp;

public class Item {
    private final String title;
    private final String poster;
    private final String description;
    private final double price;
    private final String category;
    private final String type;
    private final String maker;
    private Timestamp timestamp;
    private int key;
    private boolean listUser;

    public Item(String title, String poster, String description, double price, String categories, String type, String maker) {
        this.title = title;
        this.poster = poster;
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

    public String getPoster() {
        return poster;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isListUser() {
        return listUser;
    }

    public void setListUser(boolean listUser) {
        this.listUser = listUser;
    }
}
