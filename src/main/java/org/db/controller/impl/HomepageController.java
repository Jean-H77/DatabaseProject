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
import org.db.controller.Controller;
import org.db.model.Item;
import org.db.service.ServiceType;
import org.db.service.impl.ListingService;

import java.net.URL;
import java.util.ArrayList;
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
        for(int i = 0; i < 5; i++) categoryList.add("Cat"+i);
        categoryTypes.setItems(categoryList);
        List<String> cats = new ArrayList<>();
        cats.add("cat1");
        cats.add("cat2");
        cats.add("cat3");
        for(int i = 0; i < 25; i++) itemList.add(new Item("Item"+i, "This is Item"+i, cats, i));
        itemListView.setItems(itemList);
    }

    private void selectItemToReview(Item item) {

    }

    @FXML
    private void onSubmitClick() {

    }

    @Override
    public void destory() {

    }
}
