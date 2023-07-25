package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.db.Application;
import org.db.controller.Controller;
import org.db.controller.Navigator;
import org.db.model.LoginDetails;
import org.db.service.Service;
import org.db.service.ServiceType;
import org.db.service.impl.LoginService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Controller {

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorMessageLabel;

    private final LoginService loginService = (LoginService) getService(ServiceType.LOGIN);

    @FXML
    public void onLoginButtonClick() {
        if(usernameTextField.getText().isEmpty() || passwordTextField.getText().isEmpty())
            return;

        if(!loginService.validate(new LoginDetails())) {
            errorMessageLabel.setText("Invalid username or password.");
        }
        else {
            Navigator.switchScene("LoggedIn");
        }
    }

    @FXML
    public void onRegisterButtonClick() {
        Navigator.switchScene("Register");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
