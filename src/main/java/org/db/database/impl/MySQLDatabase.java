package org.db.database.impl;

import org.db.database.Database;
import org.db.database.Datasource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLDatabase implements Database {
    @Override
    public void login() {

    }

    @Override
    public void register() {
        try(Connection conn = Datasource.getInstance().getConnection()) {

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
