package org.db.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {


    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onLoginButtonClick() {
        System.out.println("Click login");
    }

    @FXML
    public void onRegisterButtonClick() {
        System.out.println("Click register");
    }
}
