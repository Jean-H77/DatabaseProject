package org.db.controller.impl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.db.controller.Controller;
import javafx.scene.control.Label;
import org.db.controller.Navigator;
import org.db.database.impl.MySQLDatabase;
import org.db.model.RegistrationDetails;
import org.db.model.SceneType;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private Label label_unmatchedPassword;

    @Override
    public void destory() {

    }

    // create method: I think I need to check if all text fields are not null

    public void button_registerNewClicked(ActionEvent actionEvent) {
        // check if passwords match
        if(!pf_newpassword.getText().equals(pf_confirmpassword.getText())){
            label_unmatchedPassword.setText("Passwords do not match");
        } else{
            label_unmatchedPassword.setText(null);
            createAccount();
            clearText();
            //Navigator.switchScene(SceneType.HOME_PAGE);
        }
    }

    public void clearText(){
        tf_username.clear();
        pf_newpassword.clear();
        pf_confirmpassword.clear();
        tf_firstname.clear();
        tf_lastname.clear();
        tf_email.clear();
    }

    public void button_returnToSignIn(ActionEvent actionEvent) {
        // clear text
        clearText();
        // change scene
        Navigator.switchScene(SceneType.LOGIN);
    }

    public void createAccount() {
        String username = tf_username.getText();
        String newPassword = pf_newpassword.getText();
        String firstName = tf_firstname.getText();
        String lastName = tf_lastname.getText();
        String email = tf_email.getText();

        // Create RegistrationDetails object with the user details
        RegistrationDetails registrationDetails = new RegistrationDetails(username, newPassword, firstName, lastName, email);

        // Call the register method with the created RegistrationDetails object
        MySQLDatabase reg = new MySQLDatabase();
        reg.register(registrationDetails);

        clearText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }




}
