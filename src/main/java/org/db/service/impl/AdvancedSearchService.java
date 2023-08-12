package org.db.service.impl;

import org.db.database.Database;
import org.db.model.Item;
import org.db.service.Service;

import java.util.ArrayList;
import java.util.List;

public class AdvancedSearchService extends Service {

    public AdvancedSearchService(Database database) {
        super(database);
    }

    public List<Item> searchMostExpensiveItemsByCategory(String category) {
        List<Item> list = new ArrayList<>();

        return list;
    }
}
