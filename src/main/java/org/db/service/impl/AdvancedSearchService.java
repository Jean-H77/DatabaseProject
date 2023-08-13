package org.db.service.impl;

import org.db.database.Database;
import org.db.model.Item;
import org.db.service.Service;

import java.util.*;

public class AdvancedSearchService extends Service {

    public AdvancedSearchService(Database database) {
        super(database);
    }

    public List<Item> searchMostExpensiveItemsByCategory(String category) {
        List<Item> mostExpensiveItems = new ArrayList<>();
        Map<String, HashMap<String, Set<String>>> categories = database.loadCategories();

        for (String categoryName : categories.keySet()) {
            if(category.equals("All")){
                mostExpensiveItems.addAll(findMostExpensiveItemInCategory(categoryName));
            } else if (categoryName.equals(category)){
                mostExpensiveItems.addAll(findMostExpensiveItemInCategory(categoryName));
            }
        }
        return mostExpensiveItems;
    }

    private List<Item> findMostExpensiveItemInCategory(String category) {
        List<Item> mostExpensiveItems = new ArrayList<>();
        List<Item> itemsInCategory = database.searchItems(category);

        double maxPrice = Double.MIN_VALUE;
        Item mostExpensiveItem = null;

        for (Item item : itemsInCategory) {
            if (item.getPrice() > maxPrice) {
                maxPrice = item.getPrice();
                mostExpensiveItem = item;
            }
        }

        if (mostExpensiveItem != null) {
            mostExpensiveItems.add(mostExpensiveItem);
        }

        return mostExpensiveItems;
    }
}
