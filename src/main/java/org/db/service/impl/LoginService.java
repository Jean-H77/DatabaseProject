package org.db.service.impl;

import org.db.database.Database;
import org.db.model.Details;
import org.db.model.LoginDetails;
import org.db.service.AccountService;

public class LoginService extends AccountService {

    public LoginService(Database database) {
        super(database);
    }

    @Override
    public String validate(Details details) {
        if(!(details instanceof LoginDetails))
            return "Error while logging in.";
        LoginDetails loginDetails = (LoginDetails) details;
        return "Success";
    }
}
