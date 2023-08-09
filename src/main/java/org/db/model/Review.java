package org.db.model;

import java.sql.Timestamp;

public class Review {

    private final int itemId;
    private final String description;
    private final String poster;
    private final String quality;
    private final Timestamp timestamp;

    public Review(int itemId, String description, String poster, String quality, Timestamp timestamp) {
        this.itemId = itemId;
        this.description = description;
        this.poster = poster;
        this.quality = quality;
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String getPoster() {
        return poster;
    }

    public String getQuality() {
        return quality;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public int getItemId() {
        return itemId;
    }
}
