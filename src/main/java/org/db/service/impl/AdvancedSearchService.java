package org.db.service.impl;

import org.db.controller.Navigator;
import org.db.controller.impl.HomepageController;
import org.db.database.Database;
import org.db.model.Item;
import org.db.model.Review;
import org.db.model.SceneType;
import org.db.service.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AdvancedSearchService extends Service {

    public AdvancedSearchService(Database database) {
        super(database);
    }


    public CompletableFuture<List<Item>> searchMostExpensiveItemsByCategory(String category) {
        return CompletableFuture.supplyAsync(() -> {
            List<Item> mostExpensiveItems = new ArrayList<>();
            Map<String, HashMap<String, Set<String>>> categories = database.loadCategories();
            for (String categoryName : categories.keySet()) {
                if (category.equals("All")) {
                    try {
                        mostExpensiveItems.addAll(findMostExpensiveItemInCategory(categoryName).get());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                } else if (categoryName.equals(category)) {
                    try {
                        mostExpensiveItems.addAll(findMostExpensiveItemInCategory(categoryName).get());
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return mostExpensiveItems;
        });
    }

    private CompletableFuture<List<Item>> findMostExpensiveItemInCategory(String category) {
        return CompletableFuture.supplyAsync(() -> {
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
        });
    }

    public CompletableFuture<List<Item>> searchUsersByDateAndCategory(String cat1, String cat2) {
        return CompletableFuture.supplyAsync(() -> {
            List<Item> userItems = new ArrayList<>();
            List<Item> itemsInCat1 = database.searchItems(cat1);
            List<Item> itemsInCat2 = database.searchItems(cat2);
            long cat1TS;
            long cat2TS;
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();

            for (Item cat1Item : itemsInCat1) {
                cat1TS = cat1Item.getTimestamp().getTime();
                cal1.setTimeInMillis(cat1TS);
                int cal1year = cal1.get(Calendar.YEAR);
                int cal1date = cal1.get(Calendar.DATE);
                int cal1month = cal1.get(Calendar.MONTH);
                for (Item cat2Item : itemsInCat2) {
                    cat2TS = cat2Item.getTimestamp().getTime();
                    cal2.setTimeInMillis(cat2TS);
                    int cal2year = cal2.get(Calendar.YEAR);
                    int cal2date = cal2.get(Calendar.DATE);
                    int cal2month = cal2.get(Calendar.MONTH);
                    if (
                            (cat1Item.getPoster().equals(cat2Item.getPoster()))
                                    && (cal1year == cal2year)
                                    && (cal1date == cal2date)
                                    && (cal1month == cal2month)
                    ) {
                        boolean exists = false;
                        for (Item item : userItems) {
                            if (item.getPoster().equals(cat1Item.getPoster())) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            cat1Item.setListUser(true);
                            userItems.add(cat1Item);
                        }
                    }
                }
            }
            return userItems;
        });
    }

    public CompletableFuture<List<Item>> searchUsersByMostPosts(int year, int month, int date) {
        return CompletableFuture.supplyAsync(() -> {
            List<Item> userItems = new ArrayList<>();
            List<String> posters = new ArrayList<>();
            List<Item> itemsOnDay = database.searchItemsByDay(year, month, date);
            HashMap<String, Integer> userPostCount = new HashMap<>();
            int mostPosts = 0;
            List<String> mostPostsUser = new ArrayList<>();

            for (Item item : itemsOnDay) {
                boolean exists = false;
                for (String poster : posters) {
                    if (poster.equals(item.getPoster())) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    posters.add(item.getPoster());
                }
            }

            for (String poster : posters) {
                int counter = 0;
                for (Item item : itemsOnDay) {
                    if (item.getPoster().equals(poster)) {
                        counter++;
                    }
                }
                userPostCount.put(poster, counter);
            }

            for (Map.Entry<String, Integer> entry : userPostCount.entrySet()) {
                if (entry.getValue() > mostPosts) {
                    mostPosts = entry.getValue();
                    mostPostsUser.clear();
                    mostPostsUser.add(entry.getKey());
                } else if (entry.getValue() == mostPosts) {
                    mostPostsUser.add(entry.getKey());
                }
            }

            for (String user : mostPostsUser) {
                for (Item item : itemsOnDay) {
                    if (item.getPoster().equals(user)) {
                        item.setListUser(true);
                        userItems.add(item);
                        break;
                    }
                }
            }

            return userItems;
        });
    }

    public CompletableFuture<List<Item>> searchItemsByReviewQualityType(String qualityType) {
        return CompletableFuture.supplyAsync(() -> {
            List<Item> itemsWithRatedQuality = new ArrayList<>();
            int itemCount = database.getTotalItemCount();
            for (int i = 0; i < itemCount; i++) {
                List<Review> reviews = ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).listingService.getReviews(i);
                for (Review review : reviews) {
                    if (review.getQuality().equals(qualityType)) {
                        Item item = database.getItem(review.getItemId());
                        itemsWithRatedQuality.add(item);
                    }
                }
            }
            return itemsWithRatedQuality;
        });
    }

    public CompletableFuture<List<String>> joinUserByReviewQualityType(String qualityType) {
        return CompletableFuture.supplyAsync(() -> {
            List<String> userRatings = new ArrayList<>();
            int itemCount = database.getTotalItemCount();
            for (int i = 0; i < itemCount; i++) {
                List<Review> reviews = ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).listingService.getReviews(i);
                for (Review review : reviews) {
                    if (review.getQuality().equals(qualityType)) {
                        String username = review.getPoster();
                        String rating = review.getQuality();
                        int id = review.getItemId();
                        Item item = database.getItem(id);
                        String itemName = item.getTitle();
                        String userRating = "User: " + username + "\nRating: " + rating + "\nItem: " + itemName;
                        userRatings.add(userRating);
                    }
                }
            }
            return userRatings;
        });
    }

    public CompletableFuture<List<String>> getUsersWithPoorOrNoReviews() {
        return CompletableFuture.supplyAsync(() -> {
            List<String> filteredUserRatings;
            filteredUserRatings = database.getUsersWithPoorOrNoReviewsQuery();
            return filteredUserRatings;
        });
    }

    public CompletableFuture<List<Item>> filterPositiveCommentsOnSearchButtonClick(String selectedUser) {
        return CompletableFuture.supplyAsync(() -> {
            List<Item> filteredUserRatings;
            filteredUserRatings = database.getItemsByUserWithoutPoorOrFairReviews(selectedUser);
            return filteredUserRatings;
        });
    }


    public CompletableFuture<List<String>> getAllUserNames(){
        return CompletableFuture.supplyAsync(() -> {
            List<String> allUsersList;
            allUsersList = database.getAllUsernames();
            return allUsersList;
        });
    }
}
