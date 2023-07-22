package org.db.database;

import org.db.model.LoginDetails;
import org.db.model.RegistrationDetails;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Database {

    protected Connection getConnection() throws SQLException {
        return Datasource.getInstance().getConnection();
    }

    public abstract void login(LoginDetails loginDetails);

    public abstract void register(RegistrationDetails registrationDetails);
}
