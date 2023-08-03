package org.db.database;

import org.db.model.HomepageDetails;
import org.db.model.LoginDetails;
import org.db.model.RegistrationDetails;
import org.db.model.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;

public abstract class Database {

    protected Connection getConnection() throws SQLException {
        return Datasource.getInstance().getConnection();
    }

    public abstract void createUser(RegistrationDetails registrationDetails);

    public abstract Optional<User> getUser(LoginDetails details);

    public abstract boolean exists(String username, String email);

    public abstract void addItem(HomepageDetails homepageDetails);

    public abstract int getPostCountForUserOnDate(String username, Date date);

}
