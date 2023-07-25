package org.db.service.impl;

import org.db.database.Database;
import org.db.model.LoginDetails;
import org.db.service.Service;

public class LoginService extends Service {

    public LoginService(Database database) {
        super(database);
    }

    public boolean validate(LoginDetails loginDetails) {

        return true; // temporarily default true for testing
    }
}
