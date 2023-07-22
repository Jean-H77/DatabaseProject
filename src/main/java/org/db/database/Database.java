package org.db.database;

import org.db.model.LoginDetails;
import org.db.model.RegistrationDetails;

public abstract class Database {

    public abstract void login(LoginDetails loginDetails);

    public abstract void register(RegistrationDetails registrationDetails);
}
