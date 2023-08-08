package org.db.database.impl;

import org.db.Client;
import org.db.database.Database;
import org.db.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLDatabase extends Database {


    @Override
    public void createUser(RegistrationDetails registrationDetails) {
        String query = "INSERT INTO users(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL) VALUES(?,?,?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, registrationDetails.getUsername());
            preparedStatement.setString(2, registrationDetails.getPassword());
            preparedStatement.setString(3, registrationDetails.getFirstName());
            preparedStatement.setString(4, registrationDetails.getLastName());
            preparedStatement.setString(5, registrationDetails.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> getUser(LoginDetails details) {
        String query = "SELECT * FROM users WHERE USERNAME = ? AND PASSWORD = ? LIMIT 1";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, details.getUsername());
            stmt.setString(2, details.getPassword());
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.isBeforeFirst()) {
                    resultSet.next();
                    User user = new User(
                            resultSet.getString("USERNAME"),
                            resultSet.getString("PASSWORD"),
                            resultSet.getString("FIRST_NAME"),
                            resultSet.getString("LAST_NAME"),
                            resultSet.getString("EMAIL")
                    );
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean exists(String username, String email) {
        String query = "SELECT * FROM users WHERE USERNAME = ? OR EMAIL = ? LIMIT 1";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, email);
            try (ResultSet resultSet = stmt.executeQuery()) {
                return resultSet.isBeforeFirst();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // original
//    public void addItem(Item item) {
//        String query = "INSERT INTO items(TITLE, DESCRIPTION, CATEGORY, PRICE, USERNAME) VALUES(?,?,?,?,?)";
//        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
//            preparedStatement.setString(1, item.getTitle());
//            preparedStatement.setString(2, item.getDescription());
//            preparedStatement.setString(3, item.getCategory());
//            preparedStatement.setDouble(4, item.getPrice());
//            preparedStatement.setString(5, Client.getMyUser().getUsername());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void addItem(Item item) {
        String insertItemQuery = "INSERT INTO items(TITLE, DESCRIPTION, PRICE, USERNAME) VALUES(?,?,?,?)";
        String insertCategoryQuery = "INSERT INTO item_categories(ITEM_ID, CATEGORY_ID) VALUES(?, ?)";

        try (PreparedStatement insertItem = getConnection().prepareStatement(insertItemQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement insertCategory = getConnection().prepareStatement(insertCategoryQuery)) {

            insertItem.setString(1, item.getTitle());
            insertItem.setString(2, item.getDescription());
            insertItem.setDouble(3, item.getPrice());
            insertItem.setString(4, Client.getMyUser().getUsername());

            int affectedRows = insertItem.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertItem.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int itemId = generatedKeys.getInt(1);

                    for (String category : item.getCategories()) {
                        insertCategory.setInt(1, itemId);
                        // insert category here
                        insertCategory.executeUpdate();
                    }
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LocalDate> getLastThreePostings(TABLE table) {
        String query = "SELECT POSTED_TIMESTAMP FROM " + table.getCleanName() + " WHERE USERNAME = ? ORDER BY POSTED_TIMESTAMP DESC LIMIT 3";
        List<LocalDate> dates = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, Client.getMyUser().getUsername());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    System.out.println("Got timestamp");
                    dates.add(resultSet.getTimestamp("POSTED_TIMESTAMP").toLocalDateTime().toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    public List<Item> searchItems(String categorySearch) {
        List<Item> itemList = new ArrayList<Item>();
        /*String query = "SELECT * FROM items";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getString("CATEGORY").equals(categorySearch)) {
                    String title = resultSet.getString("TITLE");
                    String description = resultSet.getString("DESCRIPTION");
                    String category = resultSet.getString("CATEGORY");
                    double price = resultSet.getDouble("PRICE");

                    Item item = new Item(title, description, category, price, poster);

                    itemList.add(item);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }*/

        return itemList;

    }

}
