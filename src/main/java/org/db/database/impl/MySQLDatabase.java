package org.db.database.impl;

import org.db.Client;
import org.db.database.Database;
import org.db.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

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

    public void addItem(Item item) {
        String insertItemQuery = "INSERT INTO items(TITLE, DESCRIPTION, PRICE, USERNAME) VALUES(?,?,?,?)";
        String insertCategoryQuery = "INSERT INTO item_categories(ITEM_ID, TYPE_ID, CAT_MAKER_ID, CAT_TYPE_ID) VALUES(?, ?, ?, ?)";
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
                    String category = item.getCategory();
                    insertCategory.setInt(1, itemId);
                    insertCategory.setInt(2, getCategoryID(category, CategoryType.BASE));
                    insertCategory.setInt(3, getCategoryID(item.getMaker(), CategoryType.MAKER));
                    insertCategory.setInt(4, getCategoryID(item.getType(), CategoryType.TYPE));

                    insertCategory.executeUpdate();

                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCategoryID(String categoryName, CategoryType type) {
        String query = type.getQuery();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, categoryName);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(type.getPrimaryKeyName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return a default value if category ID is not found
    }

    @Override
    public List<LocalDate> getLastThreePostings(Table table) {
        String query = "SELECT POSTED_TIMESTAMP FROM " + table.getCleanName() + " WHERE USERNAME = ? ORDER BY POSTED_TIMESTAMP DESC LIMIT 3";
        List<LocalDate> dates = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, Client.getMyUser().getUsername());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    dates.add(resultSet.getTimestamp("POSTED_TIMESTAMP").toLocalDateTime().toLocalDate());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }

    @Override
    public Item getItem(int itemID) {
        String title = "Item Not Found";
        String description = "N/A";
        double price = 0.0;
        String category = "";
        String type = "";
        String maker = "";
        String poster = "";
        Timestamp timestamp = null;
        String query = "SELECT * FROM items WHERE ITEM_ID = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, itemID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    title = resultSet.getString("TITLE");
                    description = resultSet.getString("DESCRIPTION");
                    price = resultSet.getDouble("PRICE");
                    poster = resultSet.getString("USERNAME");
                    timestamp = resultSet.getTimestamp("POSTED_TIMESTAMP");

                    Category cat = getCategories(itemID);
                    if(cat != null) {
                        category = getBaseCategoryName(cat);
                        type = getCategoryTypeName(cat);
                        maker = getCategoryMakerName(cat);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Item item = new Item(title, poster, description, price, category, type, maker);
        item.setKey(itemID);
        item.setTimestamp(timestamp);
        return item;
    }

    @Override
    public String getCategoryTypeName(Category category) {
        String query = "SELECT TYPE_NAME FROM categoryTypes WHERE TYPE_ID = ? AND CATEGORY_ID = ?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, category.getTypeCategoryId());
            preparedStatement.setInt(2, category.getBaseCategoryId());
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    return rs.getString("TYPE_NAME");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getCategoryMakerName(Category category) {
        String query = "SELECT MAKER_NAME FROM categoryMakers WHERE MAKER_ID = ? AND CATEGORY_ID = ?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, category.getMakerCategoryId());
            preparedStatement.setInt(2, category.getBaseCategoryId());
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    return rs.getString("MAKER_NAME");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public String getBaseCategoryName(Category category) {
        String query = "SELECT CATEGORY_NAME FROM categories WHERE CATEGORY_ID = ?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, category.getBaseCategoryId());
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    return rs.getString("CATEGORY_NAME");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public Category getCategories(int itemID) {
        String query = "SELECT * FROM item_categories WHERE ITEM_ID = ? LIMIT 1";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, itemID);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                if(rs.next()) {
                    return new Category(
                      rs.getInt("TYPE_ID"),
                      rs.getInt("CAT_MAKER_ID"),
                      rs.getInt("CAT_TYPE_ID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Item> searchItems(String categorySearch) {
        List<Item> itemList = new ArrayList<>();
        int catID = getCategoryID(categorySearch, CategoryType.BASE);
        String query = "SELECT ITEM_ID FROM item_categories WHERE TYPE_ID = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setInt(1, catID);
            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    itemList.add(getItem(resultSet.getInt("ITEM_ID")));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public HashMap<String, HashMap<String, Set<String>>> loadCategories() {
        HashMap<String, HashMap<String, Set<String>>> categories = new HashMap<>();
        String query = "SELECT c.CATEGORY_NAME, m.MAKER_NAME, t.TYPE_NAME " +
                "FROM categories c " +
                "INNER JOIN categoryMakers m ON c.CATEGORY_ID = m.CATEGORY_ID " +
                "INNER JOIN categoryTypes t ON c.CATEGORY_ID = t.CATEGORY_ID";
        try(PreparedStatement statement = getConnection().prepareStatement(query)) {
            try(ResultSet resultSet = statement.executeQuery()) {
              while(resultSet.next()) {
                  String categoryName = resultSet.getString("CATEGORY_NAME");
                  String categoryMaker = resultSet.getString("MAKER_NAME");
                  String categoryType = resultSet.getString("TYPE_NAME");

                  HashMap<String, Set<String>> map = categories.computeIfAbsent(categoryName, x -> new HashMap<>());
                  Set<String> typeList = map.computeIfAbsent("type", x -> new HashSet<>());
                  typeList.add(categoryType);

                  Set<String> makerList = map.computeIfAbsent("maker", x -> new HashSet<>());
                  makerList.add(categoryMaker);
              }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public List<Review> getReviews(int itemId) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM reviews WHERE ITEM_ID = ?";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, itemId);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    String username = rs.getString("USERNAME");
                    String review = rs.getString("REVIEW");
                    String quality = rs.getString("QUALITY");
                    Timestamp timestamp = rs.getTimestamp("POSTED_TIMESTAMP");
                    reviews.add(new Review(itemId, review, username, quality, timestamp));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public void postReview(Review review) {
        String query = "INSERT INTO reviews (ITEM_ID, USERNAME, REVIEW, QUALITY) VALUES (?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, review.getItemId());
            preparedStatement.setString(2, review.getPoster());
            preparedStatement.setString(3,review.getDescription());
            preparedStatement.setString(4, review.getQuality());
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating review failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
