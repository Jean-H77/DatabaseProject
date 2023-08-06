package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.db.Client;

import java.io.IOException;

public class CardComponent extends VBox {

    @FXML
    private Text itemName;

    @FXML
    private Label description;

    @FXML
    private ImageView image;

    @FXML
    private void onReviewButtonClicked() {

    }

    public CardComponent(String itemName, String description) {
        try {
            FXMLLoader loader = new FXMLLoader(Client.class.getResource("card.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.itemName.setText(itemName);
        this.description.setText(description);
    }
}
