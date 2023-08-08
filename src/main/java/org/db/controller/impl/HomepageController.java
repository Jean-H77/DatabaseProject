package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.db.Client;
import org.db.controller.Controller;
import org.db.controller.Navigator;
import org.db.database.Database;
import org.db.model.Item;
import org.db.model.SceneType;
import org.db.model.User;
import org.db.service.ServiceType;
import org.db.service.impl.ListingService;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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

    @FXML
    ComboBox cb_categorySearch;

    @FXML
    CheckBox cb_Apparel;
    @FXML
    CheckBox cb_Appliances;
    @FXML
    CheckBox cb_Electronics;


    public final ListingService listingService = (ListingService) getService(ServiceType.HOMEPAGE);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cb_category.getItems().addAll("category1", "category2", "category3");
        cb_categorySearch.getItems().addAll("category1", "category2", "category3");
    }

    public void button_itemSubmitClicked(){
        User loggedInUser = Client.getMyUser();
        if (loggedInUser != null)
            l_welcomeUser.setText(loggedInUser.getUsername());

        if(tf_tittle.getText().isEmpty() || ta_description.getText().isEmpty() || tf_price.getText().isEmpty())
            return;

        String tittle = tf_tittle.getText();
        String description = ta_description.getText();
        //String category = (String) cb_category.getValue(); // need to be changed
        double price = Double.parseDouble(tf_price.getText());

        List<String> selectedCategories = new ArrayList<>();
        if (cb_Apparel.isSelected()) {
            selectedCategories.add("apparel");
        }
        if (cb_Appliances.isSelected()) {
            selectedCategories.add("appliances");
        }
        if (cb_Electronics.isSelected()) {
            selectedCategories.add("electronics");
        }

        Item item = new Item(tittle,description,selectedCategories,price);

        String response = listingService.getResponse(Database.TABLE.ITEMS);


        l_itemStatus.setText(response);

        if(response.equals("Success")){
            listingService.addItem(item);
        }

        destory();
    }

    public void button_logoutClicked(){
        Client.getInstance().setMyUser(null);
        l_welcomeUser.setText("");
        l_itemStatus.setText("");
        destory();
        Navigator.switchScene(SceneType.LOGIN);
    }

    public void button_itemSearchClicked(){
        List<String> titleList = new ArrayList<>();
        List<String> descList = new ArrayList<>();

        String category = (String) cb_categorySearch.getValue();
        List<Item> itemList = listingService.search(category);

        for (Item item : itemList) {
            titleList.add(item.getTitle());
            System.out.println(item.getTitle());
            descList.add(item.getDescription());
            System.out.println(item.getDescription());
        }
        reloadItems(titleList, descList);
    }

    public void reloadItems(List<String> titleList, List<String> descList) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        hBox.paddingProperty().set(new Insets(25,25,25,25));
        hBox.spacingProperty().set(10);
        int extra = 0;

        for(int i = 0; i < titleList.size(); i++) {
            if(i%4 == 0 && i != 0) {
                vBox.getChildren().add(hBox);
                hBox = new HBox();
                hBox.paddingProperty().set(new Insets(25, 25, 25, 25));
                hBox.spacingProperty().set(10);
            }
            else {
                extra++;
            }
            hBox.getChildren().add(new CardComponent(titleList.get(i), descList.get(i)));
            if ((i == (titleList.size() - 1)) && (extra%4 != 0)) {
                vBox.getChildren().add(hBox);
            }
        }
        scrollContainer.setContent(vBox);
    }

    @Override
    public void destory() {
        tf_tittle.clear();
        ta_description.clear();
        tf_price.clear();
    }
}
