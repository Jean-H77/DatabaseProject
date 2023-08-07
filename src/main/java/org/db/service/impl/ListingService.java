package org.db.service.impl;

import org.db.database.Database;
import org.db.model.Item;
import org.db.service.Service;

import java.time.LocalDate;
import java.util.List;


public class ListingService extends Service {

    public ListingService(Database database) {
        super(database);
    }

    public int getPostCount(Database.TABLE table) {
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

    public String getResponse(Database.TABLE table) {
        if(getPostCount(table) < 3)
            return "Success";
        return "You cannot post more than 3 items per day.";
    }

    public void addItem(Item item) {
       database.addItem(item);
    }
}
