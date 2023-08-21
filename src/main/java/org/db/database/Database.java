package org.db.database;

import org.db.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class Database {


    protected Connection getConnection() throws SQLException {
        return Datasource.getInstance().getConnection();
    }

    public abstract void createUser(RegistrationDetails registrationDetails);

    public abstract Optional<User> getUser(LoginDetails details);

    public abstract boolean exists(String username, String email);

    public abstract void addItem(Item item);

    public abstract List<LocalDate> getLastThreePostings(Table table);

    public abstract List<Item> searchItems(String category);

    public abstract List<Item> searchMostExpensiveItemsInEachCategory(String category);

    public abstract List<Item> searchItemsByDay(int year, int month, int date);

    public abstract HashMap<String, HashMap<String, Set<String>>> loadCategories();

    public abstract int getCategoryID(String categoryName, CategoryType type);

    public abstract Item getItem(int itemID);

    public abstract Category getCategories(int itemID);

    public abstract String getCategoryTypeName(Category category);

    public abstract String getCategoryMakerName(Category category);

    public abstract String getBaseCategoryName(Category category);

    public abstract List<Review> getReviews(int itemId);

    public abstract void postReview(Review review);

    public abstract int getTotalItemCount();

    public abstract List<String> getUsersWithPoorOrNoReviewsQuery();

    public abstract List<String> getAllUsernames();

    public abstract List<Item> getItemsByUserWithoutPoorOrFairReviews(String username);

    public enum Table {
        ITEMS("items"),
        REVIEWS("reviews");

        private final String cleanName;

        Table(String cleanName) {
            this.cleanName = cleanName;
        }

        public String getCleanName() {
            return cleanName;
        }
    }
}
