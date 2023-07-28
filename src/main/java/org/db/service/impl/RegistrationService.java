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

        if(!registrationDetails.getPassword().equals(registrationDetails.getConfirmPassword()))
            return "Passwords must match";

        if(registrationDetails.getUsername().length() > 16)
            return "Passwords must not be more than 16 characters long";

      //  if(database.getUser(registrationDetails).isPresent())
        //    return "Account aleady exists.";

        database.createUser(registrationDetails);

        return "Success";
    }
}
