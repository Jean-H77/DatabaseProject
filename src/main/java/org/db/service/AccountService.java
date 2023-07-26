package org.db.service;

import org.db.database.Database;

public abstract class AccountService extends Service {

    protected AccountService(Database database) {
        super(database);
    }

    public abstract String validate(Object object);
}
