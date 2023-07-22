package org.db.database;

import org.db.database.impl.MySQLDatabase;

public enum DatabaseType {
    MYSQL(MySQLDatabase.class),
    POSTGRES(null);

    private final Class<?> classz;

    DatabaseType(Class<?> classz) {
        this.classz = classz;
    }

    public Database getDatabase()  {
        try {
            return (Database) classz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
