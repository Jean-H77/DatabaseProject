package org.db.service.impl;

import org.db.database.Database;
import org.db.model.Details;
import org.db.model.RegistrationDetails;
import org.db.service.AccountService;

public class RegistrationService extends AccountService {

    public RegistrationService(Database database) {
        super(database);
    }

    @Override
    public String validate(Details details) {
        if(!(details instanceof RegistrationDetails))
            return "Error while registering";

        RegistrationDetails registrationDetails = (RegistrationDetails) details;

        if(database.doesAccountExist(registrationDetails))
            return "Account already exists.";

        return "Success";
    }
}
