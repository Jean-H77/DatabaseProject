package org.db.service.impl;

import org.db.Client;
import org.db.controller.Navigator;
import org.db.controller.impl.HomepageController;
import org.db.database.Database;
import org.db.model.Item;
import org.db.model.Review;
import org.db.model.SceneType;
import org.db.model.User;
import org.db.service.Service;
import org.db.service.ServiceType;

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

    public List<Item> searchUsersByDateAndCategory(String cat1, String cat2) {
        List<Item> userItems = new ArrayList<>();
        // W.I.P.
        return userItems;
    }

    public List<Item> searchItemsByReviewQualityType(String qualityType) {
        List<Item> itemsWithRatedQuality = new ArrayList<>();
        int itemCount = database.getTotalItemCount();
        for(int i = 0; i < itemCount; i++){
            List<Review> reviews = ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).listingService.getReviews(i);
            for (Review review : reviews) {
                if (review.getQuality().equals(qualityType)) {
                    Item item = database.getItem(review.getItemId());
                    itemsWithRatedQuality.add(item);
                }
            }
        }
        return itemsWithRatedQuality;
    }
}
