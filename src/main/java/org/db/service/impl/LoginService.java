package org.db.service.impl;

import org.db.database.Database;
import org.db.model.LoginDetails;
import org.db.service.AccountService;

public class LoginService extends AccountService {

    public LoginService(Database database) {
        super(database);
    }

    @Override
    public String validate(Object object) {
        if(!(object instanceof LoginDetails))
            return "Error while logging in.";
        LoginDetails loginDetails = (LoginDetails) object;
        return "Success";
    }
}
