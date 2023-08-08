package org.db.controller.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.PopOver;
import org.db.Client;
import org.db.controller.Controller;
import org.db.database.Database;
import org.db.model.Item;
import org.db.model.User;
import org.db.service.ServiceType;
import org.db.service.impl.ListingService;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class HomepageController implements Controller{

    public final ListingService listingService = (ListingService) getService(ServiceType.HOMEPAGE);

    private final ObservableList<String> categoryList = FXCollections.observableArrayList();

    private final ObservableList<Item> itemList = FXCollections.observableArrayList();

    @FXML
    private TextField itemName;

    @FXML
    private TextArea description;

    @FXML
    private TextField price;

    @FXML
    private CheckListView<String> typeCheckListView;

    @FXML
    private CheckListView<String> makerCheckListView;

    @FXML
    private ComboBox<String> categoryTypes;

    @FXML
    private ListView<Item> itemListView;

    @FXML
    private ComboBox<String> searchComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemListView.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                if(item == null || empty) return;
                setOnMouseClicked(event -> selectItemToReview(item));
                setGraphic(new Text(item.getTitle()));
            }
        });
        String[] Categories = {"Apparel","Appliances","Electronics","Footwear","Jewelry"};
        for(int i = 0; i < 5; i++) categoryList.add(Categories[i]);

        categoryTypes.setItems(categoryList);
        List<String> cats = new ArrayList<>();

        cats.add("thing1");
        cats.add("thing2");
        cats.add("thing3");
        cats.add("thing4");

//        for(int i = 0; i < 25; i++) itemList.add(new Item("Item"+i, "This is Item"+i, cats, i));
        for(int i = 0; i < 25; i++) itemList.add(new Item("Item Name"+i, "This is Item description"+i,-1,null,null,null ));
        itemListView.setItems(itemList);
    }

    private void selectItemToReview(Item item) {

    }

    @FXML
    private void onSubmitClick() {

        String itemNameValue = itemName.getText();
        String descriptionValue = description.getText();
        double priceinput = Double.parseDouble(price.getText());
        String selectedCategory = categoryTypes.getValue();
        List<String> selectedTypes = typeCheckListView.getCheckModel().getCheckedItems();
        List<String> selectedMakers = makerCheckListView.getCheckModel().getCheckedItems();

        // Create the item object
        Item newItem = new Item(itemNameValue, descriptionValue, priceinput, Collections.singletonList(selectedCategory), selectedTypes, selectedMakers);

        String response = listingService.getResponse(Database.TABLE.ITEMS);

        if(response.equals("Success")){
            listingService.addItem(newItem);
        }
        destory();
    }

    @Override
    public void destory() {
        itemName.clear();
        description.clear();
        price.clear();
        categoryTypes.getSelectionModel().clearSelection();
        typeCheckListView.getCheckModel().clearChecks();
        makerCheckListView.getCheckModel().clearChecks();

    }
}
