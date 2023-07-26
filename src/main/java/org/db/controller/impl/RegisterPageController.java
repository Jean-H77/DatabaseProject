package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.db.controller.Controller;
import org.db.controller.Navigator;
import org.db.model.RegistrationDetails;
import org.db.model.SceneType;
import org.db.service.ServiceType;
import org.db.service.impl.RegistrationService;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPageController implements Controller {

    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField pf_newpassword;
    @FXML
    private PasswordField pf_confirmpassword;
    @FXML
    private TextField tf_firstname;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_email;

    @FXML
    private Label label_unmatchedPassword;

    private final RegistrationService registrationService = (RegistrationService) getService(ServiceType.REGISTRATION);

    @Override
    public void destory() {
        tf_username.clear();
        pf_newpassword.clear();
        pf_confirmpassword.clear();
        tf_firstname.clear();
        tf_lastname.clear();
        tf_email.clear();
    }

    @FXML
    public void button_registerNewClicked() {
        if(!pf_newpassword.getText().equals(pf_confirmpassword.getText())) {
            label_unmatchedPassword.setText("Passwords do not match");
            return;
        }
        String username = tf_username.getText();
        String newPassword = pf_newpassword.getText();
        String firstName = tf_firstname.getText();
        String lastName = tf_lastname.getText();
        String email = tf_email.getText();

        RegistrationDetails registrationDetails = new RegistrationDetails(username, newPassword, firstName, lastName, email);
        String response = registrationService.validate(registrationDetails);

        if(response.equals("Success")) {
            Navigator.switchScene(SceneType.HOME_PAGE);
            return;
        }

        destory();
        // print error message from response
    }

    @FXML
    public void button_returnToSignIn() {
        Navigator.switchScene(SceneType.LOGIN);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
