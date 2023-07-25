package org.db.controller.impl;

import java.net.URL;
import java.util.ResourceBundle;
import org.db.controller.Controller;

import javafx.scene.control.Button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
//--
public class LoggedInPageController implements Controller{

    // link id's

    @FXML
    private Label label_signedin;

    @FXML
    private Button button_logout;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        button_logout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event){

            }
        });

    }
}
