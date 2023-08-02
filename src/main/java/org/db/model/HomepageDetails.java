package org.db.model;

import java.sql.Date;

public class HomepageDetails {
    private String title;
    private String description;
    private String category;
    private double price;
    private String username;
    private Date numOfPost;

    public HomepageDetails(String title, String description, String category, double price, String username, Date numOfPost) {

        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.username = username;
        this.numOfPost = numOfPost;
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

    public String getUsername() {
        return username;
    }

    public Date getDate(){
        return numOfPost;
    }

}
