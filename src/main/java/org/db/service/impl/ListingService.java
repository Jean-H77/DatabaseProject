package org.db.service.impl;

import org.db.database.Database;
import org.db.model.Item;
import org.db.model.Review;
import org.db.service.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class ListingService extends Service {

    public ListingService(Database database) {
        super(database);
    }

    public int getPostCount(Database.Table table) {
        List<LocalDate> localDates = database.getLastThreePostings(table);
        int size = localDates.size();
        if(size < 3)
            return size;
        int sameDayCount = 0;
        for(LocalDate localDate : localDates) {
            if (localDate.equals(LocalDate.now()))
                sameDayCount++;
        }
        return sameDayCount;
    }

    public List<Item> search(String category) {
        return database.searchItems(category);
    }

    public String getResponse(Database.Table table) {
        if(getPostCount(table) < 3)
            return "Success";
        return "You cannot post more than 3 items per day.";
    }

    public void addItem(Item item) {
       database.addItem(item);
    }

    public List<Item> searchItems(String category) {
        return database.searchItems(category);
    }

    public HashMap<String, HashMap<String, Set<String>>> loadCategories() {
        return database.loadCategories();
    }

    public void postReview(Review review) {
        database.postReview(review);
    }

    public List<Review> getReviews(int itemId) {
        return database.getReviews(itemId);
    }

}
