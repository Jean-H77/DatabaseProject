package org.db.service.impl;

import org.db.database.Database;
import org.db.model.HomepageDetails;
import org.db.service.Service;


public class HomepageService extends Service {

    public HomepageService(Database database) {
        super(database);
    }

    // here is where we will make the database call to insert new item

    public String validate(HomepageDetails homepageDetails){
        // Check the number of posts made by the user on the current date
        int postCountToday = database.getPostCountForUserOnDate(homepageDetails.getUsername(), homepageDetails.getDate());
        if (postCountToday >= 3) {
            return"Daily post limit\n(3 posts per day).";
        }
        database.addItem(homepageDetails);
        return "Success";
    }


}
