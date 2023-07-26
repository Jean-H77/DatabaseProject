package org.db.database;

import org.db.model.Details;
import org.db.model.RegistrationDetails;
import org.db.model.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public abstract class Database {

    protected Connection getConnection() throws SQLException {
        return Datasource.getInstance().getConnection();
    }

    public abstract void createUser(RegistrationDetails registrationDetails);

    public abstract Optional<User> getUser(Details details);
}
