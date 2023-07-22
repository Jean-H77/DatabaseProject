package org.db.database.impl;

import org.db.database.Database;
import org.db.database.Datasource;
import org.db.model.LoginDetails;
import org.db.model.RegistrationDetails;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDatabase extends Database {


    @Override
    public void login(LoginDetails loginDetails) {

    }

    @Override
    public void register(RegistrationDetails registrationDetails) {
        try(Connection conn = Datasource.getInstance().getConnection()) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
