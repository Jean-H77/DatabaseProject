package org.db.model;

public class RegistrationDetails extends Details {

    private final String firstName;
    private final String lastName;
    private final String email;
    private final String confirmPassword;

    public RegistrationDetails(String username, String password, String firstName, String lastName, String email, String confirmPassword) {
        super(username, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }
}

