package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.db.controller.Controller;
import org.db.controller.Navigator;
import org.db.model.LoginDetails;
import org.db.model.SceneType;
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
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();
        String response = loginService.validate(new LoginDetails(username, password));

        if(response.equals("Success")) {
            // pass current user logged in into homepage
            Navigator.setLoggedInUsername(username);
            // switch scene
            Navigator.switchScene(SceneType.HOME_PAGE);
            return;
        }
        destory();
        errorMessageLabel.setText("Invalid username or password.");
    }

    @FXML
    public void onRegisterButtonClick() {
        Navigator.switchScene(SceneType.REGISTER);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void destory() {
        usernameTextField.clear();
        passwordTextField.clear();
        errorMessageLabel.setText("");
    }
}
