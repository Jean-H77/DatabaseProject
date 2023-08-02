package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.db.controller.Controller;
import org.db.controller.Navigator;
import org.db.database.Database;
import org.db.database.impl.MySQLDatabase;
import org.db.model.HomepageDetails;
import org.db.model.SceneType;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomepageController implements Controller{

    @FXML
    ScrollPane scrollContainer;

    @FXML
    TextField tf_tittle;
    @FXML
    TextArea ta_description;
    @FXML
    ComboBox cb_category;
    @FXML
    TextField tf_price;
    @FXML
    Label l_itemStatus;
    @FXML
    Label l_welcomeUser;

    private String loggedInUsername = null;


    // JOHN USE GROUP LAYOUT FOR THIS
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.paddingProperty().set(new Insets(25,25,25,25));
        hBox.spacingProperty().set(10);

        cb_category.getItems().addAll("category1", "category2", "category3");
        Navigator.setHomepageController(this);
        l_welcomeUser.setText(loggedInUsername.toUpperCase());

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

    public void button_itemSubmitClicked(){
        String tittle = tf_tittle.getText();
        String description = ta_description.getText();
        String category = (String) cb_category.getValue();
        double price = Double.parseDouble(tf_price.getText());
        String username = loggedInUsername;
        Date numOfPost = Date.valueOf(LocalDate.now());

        Database database = new MySQLDatabase();

        // Check the number of posts made by the user on the current date
        int postCountToday = ((MySQLDatabase) database).getPostCountForUserOnDate(loggedInUsername, numOfPost);
        if (postCountToday >= 3) {
            l_itemStatus.setText("Daily post limit\n(3 posts per day).");
            return;
        }

        HomepageDetails homepageDetails = new HomepageDetails(tittle,description,category,price,username,numOfPost);

        database.addItem(homepageDetails);

        l_itemStatus.setText("New Item Added");

        destory();
        return;
    }

    public void setLoggedInUser(String username) {
        this.loggedInUsername = username;
    }

    public void button_logoutClicked(){
        l_welcomeUser.setText("");
        Navigator.switchScene(SceneType.LOGIN);
    }

    @Override
    public void destory() {
        tf_tittle.clear();
        ta_description.clear();
        tf_price.clear();
    }
}
