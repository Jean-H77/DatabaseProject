package org.db.database.impl;

import org.db.database.Database;
import org.db.model.Details;
import org.db.model.LoginDetails;
import org.db.model.RegistrationDetails;
import org.db.model.User;

import java.sql.*;
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
    public Optional<User> getUser(Details details) {
        String query = "SELECT * FROM users WHERE USERNAME = ? AND " + (details instanceof LoginDetails ?
                 "PASSWORD" : "EMAIL") + " = ? LIMIT 1";
        boolean result = false;
        ResultSet resultSet;
        try(PreparedStatement stmt = getConnection().prepareStatement(query)) {
            stmt.setString(1, details.getUsername());
            stmt.setString(2, (details instanceof LoginDetails ?
                    details.getPassword() : ((RegistrationDetails)details).getEmail()));
            resultSet = stmt.executeQuery(query);
            if(resultSet.isBeforeFirst()) {
                return Optional.of(new User(
                        resultSet.getString("USERNAME"),
                        resultSet.getString("PASSWORD"),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        resultSet.getString("EMAIL")
                ));
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
