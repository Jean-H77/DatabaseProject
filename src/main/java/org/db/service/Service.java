package org.db.service;

import org.db.database.Database;

public abstract class Service {

    protected final Database database;

    protected Service(Database database) {
        this.database = database;
    }


}
