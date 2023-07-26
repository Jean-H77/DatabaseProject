package org.db.model;

public class RegistrationDetails extends Details {

    private final String firstName;
    private final String lastName;
    private final String email;

    public RegistrationDetails(String username, String password, String firstName, String lastName, String email) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}

