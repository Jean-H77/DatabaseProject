package org.db.service.impl;

import org.db.database.Database;
import org.db.model.HomepageDetails;
import org.db.model.Item;
import org.db.service.Service;

import java.util.List;


public class HomepageService extends Service {

    public HomepageService(Database database) {
        super(database);
    }

    public String validate(HomepageDetails homepageDetails){
        int postCountToday = database.getPostCountForUserOnDate(homepageDetails.getUsername(), homepageDetails.getDate());
        if (postCountToday >= 3) {
            return"Daily post limit\n(3 posts per day).";
        }
        database.addItem(homepageDetails);
        return "Success";
    }

    public List<Item> search(String category) {
        List<Item> itemList = database.searchItems(category);

        return itemList;

    }

}
