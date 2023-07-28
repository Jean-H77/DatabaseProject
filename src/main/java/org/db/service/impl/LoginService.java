package org.db.service.impl;

import org.db.database.Database;
import org.db.model.Details;
import org.db.model.LoginDetails;
import org.db.model.User;
import org.db.Client;
import org.db.service.AccountService;

public class LoginService extends AccountService {

    public LoginService(Database database) {
        super(database);
    }

    @Override
    public String validate(Details details) {
        User user;

        if(!(details instanceof LoginDetails))
            return "Error while logging in.";

        LoginDetails loginDetails = (LoginDetails) details;

        if(!database.getUser(loginDetails).isPresent())
            return "Incorrect username or password.";

        user = database.getUser(loginDetails).get();
        Client.getInstance().setMyUser(user);
        return "Success";
    }
}
