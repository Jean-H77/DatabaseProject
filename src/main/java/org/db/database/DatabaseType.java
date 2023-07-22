package org.db.database;

import org.db.database.impl.MySQLDatabase;

public enum DatabaseType {
    MYSQL(new MySQLDatabase()),
    POSTGRES(null);

    private final Database database;

    DatabaseType(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }
}
