package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.db.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;

public class HomepageController implements Controller{

    @FXML
    ScrollPane scrollContainer;

    @Override
    public void destory() {

    }

    // JOHN USE GROUP LAYOUT FOR THIS
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.paddingProperty().set(new Insets(25,25,25,25));
        hBox.spacingProperty().set(10);

        for(int i = 0; i < 20; i++) {
            if(i%4 == 0 && i != 0) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.paddingProperty().set(new Insets(25,25,25,25));
                hBox.spacingProperty().set(10);
            }
            hBox.getChildren().add(new CardComponent("Hi", "Hello here"));
        }
        scrollContainer.setContent(vBox);
    }



}
