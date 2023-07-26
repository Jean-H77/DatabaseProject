package org.db.service;

import org.db.database.Database;
import org.db.model.Details;

public abstract class AccountService extends Service {

    protected AccountService(Database database) {
        super(database);
    }

    public abstract String validate(Details details);
}
