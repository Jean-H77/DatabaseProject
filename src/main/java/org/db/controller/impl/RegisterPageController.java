package org.db.controller.impl;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.db.controller.Controller;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterPageController implements Controller {

    @FXML
    private Label label_register;
    @FXML
    private Label label_username;
    @FXML
    private Label label_newpassword;
    @FXML
    private Label label_confirmpassword;
    @FXML
    private Label label_firstname;
    @FXML
    private Label label_lastname;
    @FXML
    private Label label_email;
    @FXML
    private Label label_haveaccount;

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
    private Button button_signin;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        button_signin.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){

            }
        });

    }

    @Override
    public void destory() {

    }
}
