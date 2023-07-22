package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.db.Application;
import org.db.controller.Controller;
import org.db.model.LoginDetails;
import org.db.service.ServiceType;
import org.db.service.impl.LoginService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Controller {

    private static final LoginService LOGIN_SERVICE = (LoginService) Application.getService(ServiceType.LOGIN);

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    public void onLoginButtonClick() {
        if(usernameTextField.getText().isEmpty())
            return;
        if(passwordTextField.getText().isEmpty())
            return;
        if(!LOGIN_SERVICE.validate(new LoginDetails())) {
            errorMessageLabel.setText("Invalid username or password.");
        }
    }

    @FXML
    public void onRegisterButtonClick() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
