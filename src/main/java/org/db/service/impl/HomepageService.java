package org.db.service.impl;

import org.db.database.Database;
import org.db.model.HomepageDetails;
import org.db.service.Service;


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


}
