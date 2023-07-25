package org.db.controller.impl;

import java.net.URL;
import java.util.ResourceBundle;
import org.db.controller.Controller;

import javafx.scene.control.Button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.db.controller.Navigator;
import org.db.model.SceneType;

//--
public class LoggedInPageController implements Controller{

    // link id's

    @FXML
    private Label signedInLabel;

    @FXML
    public void onLogoutButtonClick() {
        Navigator.switchScene(SceneType.LOGIN);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
