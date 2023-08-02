package org.db.database.impl;

import org.db.database.Database;
import org.db.model.HomepageDetails;
import org.db.model.LoginDetails;
import org.db.model.RegistrationDetails;
import org.db.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MySQLDatabase extends Database {


    @Override
    public void createUser(RegistrationDetails registrationDetails) {
        String query = "INSERT INTO users(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL) VALUES(?,?,?,?,?)";
        try(PreparedStatement preparedStatement =  getConnection().prepareStatement(query)) {
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

    public void addItem(HomepageDetails homepageDetails) {
        String query = "INSERT INTO items(TITLE, DESCRIPTION, CATEGORY, PRICE, USERNAME, TIMES_POSTED) VALUES(?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, homepageDetails.getTitle());
            preparedStatement.setString(2, homepageDetails.getDescription());
            preparedStatement.setString(3, homepageDetails.getCategory());
            preparedStatement.setDouble(4, homepageDetails.getPrice());
            preparedStatement.setString(5, homepageDetails.getUsername());
            preparedStatement.setDate(6,homepageDetails.getDate());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPostCountForUserOnDate(String username, Date date) {
        String query = "SELECT COUNT(*) AS postCount FROM items WHERE USERNAME = ? AND TIMES_POSTED = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setDate(2, date);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("postCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
