package org.db.database.impl;

import org.db.database.Database;
import org.db.model.LoginDetails;
import org.db.model.RegistrationDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQLDatabase extends Database {

    @Override
    public void login(LoginDetails loginDetails) {

    }

    @Override
    public void register(RegistrationDetails registrationDetails) {
        String query = "INSERT INTO users(USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL) VALUES(?,?,?,?,?)";
        try(Connection conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(query)) {


            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
