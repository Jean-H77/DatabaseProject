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

    public abstract List<String> getBaseCategories();

    public abstract HashMap<String, HashMap<String, Set<String>>> loadCategories();

    public abstract int getCategoryID(String categoryName, CategoryType type);

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
