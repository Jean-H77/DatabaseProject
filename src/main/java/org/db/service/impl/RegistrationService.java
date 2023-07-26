package org.db.service.impl;

import org.db.database.Database;
import org.db.model.RegistrationDetails;
import org.db.service.AccountService;

public class RegistrationService extends AccountService {

    public RegistrationService(Database database) {
        super(database);
    }

    @Override
    public String validate(Object object) {
        if(!(object instanceof RegistrationDetails))
            return "Error while registering";
        RegistrationDetails registrationDetails = (RegistrationDetails) object;
        return "Success";
    }
}
