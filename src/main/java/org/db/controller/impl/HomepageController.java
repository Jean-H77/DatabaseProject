package org.db.controller.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.db.controller.Controller;
import org.db.database.Database;
import org.db.model.Item;
import org.db.service.ServiceType;
import org.db.service.impl.ListingService;

import java.net.URL;
import java.util.*;

public class HomepageController implements Controller{

    public final ListingService listingService = (ListingService) getService(ServiceType.HOMEPAGE);

    private final ObservableList<String> categoryList = FXCollections.observableArrayList();

    private final ObservableList<Item> itemList = FXCollections.observableArrayList();

    private final HashMap<String, HashMap<String, Set<String>>> categories = new HashMap<>();

    @FXML
    private TextField itemName;

    @FXML
    private TextArea description;

    @FXML
    private TextField price;

    @FXML
    private VBox typeVbox;

    @FXML
    private VBox makerVbox;

    @FXML
    private VBox itemDetails;

    @FXML
    private VBox itemReviews;

    @FXML
    private ComboBox<String> categoryTypes;

    @FXML
    private ListView<Item> itemListView;

    @FXML
    private ComboBox<String> searchComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories.putAll(listingService.loadCategories());
        typeVbox.getChildren().add(new Text("Type:"));
        makerVbox.getChildren().add(new Text("Maker:"));
        itemListView.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setOnMouseClicked(event -> selectItem(item));
                    setText(item.getTitle() + " ["+item.getMaker()+"]");
                }
            }
        });
        categoryTypes.setOnAction(event -> handleCategoryChange(categoryTypes.getValue()));
        categoryList.addAll(categories.keySet());
        categoryTypes.setItems(categoryList);
        itemListView.setItems(itemList);
        searchComboBox.getItems().addAll(categories.keySet());
    }

    private void selectItem(Item item) {
        itemDetails.getChildren().add(new Text("Details:"));
        itemDetails.getChildren().add(new Text("Title: " + item.getTitle()));
        itemDetails.getChildren().add(new Text("Description: " + item.getDescription()));
        itemDetails.getChildren().add(new Text("Price: $" + item.getPrice()));

        itemReviews.getChildren().add(new Text("Reviews:"));
        
    }

    private void handleCategoryChange(String category) {
        resetRadioButtons(typeVbox);
        resetRadioButtons(makerVbox);
        HashMap<String, Set<String>> map = categories.get(category);
        ToggleGroup typeGroup = new ToggleGroup();
        ToggleGroup makerGroup = new ToggleGroup();
        typeVbox.getChildren().add(new Text("Type:"));
        makerVbox.getChildren().add(new Text("Maker:"));
        for(Map.Entry<String, Set<String>> entry : map.entrySet()) {
            String type = entry.getKey();
            Set<String> list = entry.getValue();
            for(String str : list) {
                RadioButton radioButton = new RadioButton(str);
                if(Objects.equals(type, "type")) {
                    typeVbox.getChildren().add(radioButton);
                    radioButton.setToggleGroup(typeGroup);
                } else {
                    makerVbox.getChildren().add(radioButton);
                    radioButton.setToggleGroup(makerGroup);
                }
            }
        }
    }

    @FXML
    private void onSearchCategoryChosen() {
        itemList.clear();
        itemList.setAll(listingService.searchItems(searchComboBox.getValue()));
        itemListView.refresh();
    }

    @FXML
    private void onSubmitClick() {
        String itemNameValue = itemName.getText();
        String descriptionValue = description.getText();
        double priceInput = Double.parseDouble(price.getText());
        String selectedCategory = categoryTypes.getValue();
        String type = getSelected(typeVbox);
        String maker = getSelected(makerVbox);

        Item newItem = new Item(itemNameValue, descriptionValue, priceInput, selectedCategory, type, maker);

        String response = listingService.getResponse(Database.Table.ITEMS);

        if(response.equals("Success")){
           listingService.addItem(newItem);
           if(searchComboBox.getValue().equals(selectedCategory)) {
               itemList.add(newItem);
           }
        }
        destory();
    }

    @Override
    public void destory() {
        resetRadioButtons(typeVbox);
        resetRadioButtons(makerVbox);
        itemName.clear();
        description.clear();
        price.clear();
    }

    private void resetRadioButtons(VBox vBox) {
        vBox.getChildren().clear();
    }

    private String getSelected(VBox vBox) {
        for(Node node : vBox.getChildren()) {
            if(node instanceof RadioButton) {
                if(((RadioButton) node).isSelected())
                    return ((RadioButton) node).getText();
            }
        }
        return "None";
    }
}
