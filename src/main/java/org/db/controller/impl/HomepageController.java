package org.db.controller.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.db.Client;
import org.db.controller.Controller;
import org.db.database.Database;
import org.db.model.Item;
import org.db.model.Review;
import org.db.service.ServiceType;
import org.db.service.impl.ListingService;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class HomepageController implements Controller {

    public final ListingService listingService = (ListingService) getService(ServiceType.HOMEPAGE);

    private final ObservableList<String> categoryList = FXCollections.observableArrayList();

    private final ObservableList<Item> itemList = FXCollections.observableArrayList();

    private final ObservableList<String> reviewList = FXCollections.observableArrayList();

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
    private Text reviewName;

    @FXML
    private Text reviewDescription;

    @FXML
    private Text reviewPrice;

    @FXML
    private Text reviewCategories;

    @FXML
    private TextArea reviewTextArea;

    @FXML
    private ListView<String> pastReviewsListView;

    @FXML
    private ComboBox<String> categoryTypes;

    @FXML
    private ListView<Item> itemListView;

    @FXML
    private ComboBox<String> searchComboBox;

    @FXML
    private TitledPane reviewTitledPane;

    @FXML
    private Label addItemErrorLabel;

    @FXML
    private Label reviewItemErrorLabel;

    @FXML
    private Text posterReviewText;

    @FXML
    private ComboBox<String> qualityComboBox;

    private Item item;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories.putAll(listingService.loadCategories());
        typeVbox.getChildren().add(new Text("Type:"));
        makerVbox.getChildren().add(new Text("Maker:"));
        itemListView.setCellFactory(param -> new ListCell<Item>() {
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setOnMouseClicked(event -> selectItem(item));
                    if(item.isListUser())
                        setText(item.getPoster());
                    else
                        setText(item.getTitle() + " [" + item.getMaker() + "]");
                }
            }
        });
        categoryTypes.setOnAction(event -> handleCategoryChange(categoryTypes.getValue()));
        categoryList.addAll(categories.keySet());
        categoryTypes.setItems(categoryList);
        itemListView.setItems(itemList);
        searchComboBox.getItems().addAll(categories.keySet());
        pastReviewsListView.setItems(reviewList);
        qualityComboBox.setItems(FXCollections.observableArrayList("Excellent", "Good", "Fair", "Poor"));
        addItemErrorLabel.setText("");
        reviewItemErrorLabel.setText("");
        disableReviewPane();
    }

    private void enableReviewPane() {
        reviewTitledPane.setDisable(false);
    }

    private void disableReviewPane() {
        reviewTitledPane.setDisable(true);
    }

    private void clearItem() {
        reviewItemErrorLabel.setText("");
        reviewTextArea.setText("");
        reviewName.setText("");
        qualityComboBox.getSelectionModel().clearSelection();
        reviewDescription.setText("");
        reviewPrice.setText("");
        posterReviewText.setText("");
        reviewCategories.setText("");
        reviewList.clear();
        pastReviewsListView.refresh();
        disableReviewPane();
    }

    private void selectItem(Item item) {
        this.item = item;
        if(item.isListUser()) {
            clearItem();
            return;
        }
        enableReviewPane();
        reviewItemErrorLabel.setText("");
        reviewTextArea.setText("");
        reviewName.setText(item.getTitle());
        qualityComboBox.getSelectionModel().clearSelection();
        reviewDescription.setText(item.getDescription());
        reviewPrice.setText("$" + item.getPrice());
        posterReviewText.setText(item.getPoster());
        StringBuilder categories = new StringBuilder();
        categories.append(item.getCategory()).append(",");
        if (!Objects.equals(item.getMaker(), "None"))
            categories.append(item.getMaker()).append(",");
        if (!Objects.equals(item.getType(), "None"))
            categories.append(item.getType()).append(",");
        reviewCategories.setText(categories.substring(0, categories.lastIndexOf(",")));
        reviewList.clear();
        reviewList.setAll(listingService.getReviews(item.getKey())
                .stream().map(Review::getDescription).collect(Collectors.toList()));
        pastReviewsListView.refresh();
    }

    private void handleCategoryChange(String category) {
        resetRadioButtons(typeVbox);
        resetRadioButtons(makerVbox);
        HashMap<String, Set<String>> map = categories.get(category);
        ToggleGroup typeGroup = new ToggleGroup();
        ToggleGroup makerGroup = new ToggleGroup();
        typeVbox.getChildren().add(new Text("Type:"));
        makerVbox.getChildren().add(new Text("Maker:"));
        for (Map.Entry<String, Set<String>> entry : map.entrySet()) {
            String type = entry.getKey();
            Set<String> list = entry.getValue();
            for (String str : list) {
                RadioButton radioButton = new RadioButton(str);
                if (Objects.equals(type, "type")) {
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
        Item newItem = new Item(itemNameValue, Client.getMyUser().getUsername(), descriptionValue, priceInput, selectedCategory, type, maker);
        String response = listingService.getResponse(Database.Table.ITEMS);
        if (response.equals("Success")) {
            listingService.addItem(newItem);

            if (searchComboBox != null) {
                if (selectedCategory != null && selectedCategory.equals(searchComboBox.getValue())) {
                    onSearchCategoryChosen();
                } else {
                    searchComboBox.setValue(selectedCategory);
                    onSearchCategoryChosen();
                }
            }
        }

        addItemErrorLabel.setText(response);
        destory();
    }

    @FXML
    private void onSubmitReviewButton() {
        if (item == null) return;
        if (reviewTitledPane.isDisabled()) return;
        String postLimitResponse = listingService.getResponse(Database.Table.REVIEWS);
        if (!Objects.equals(postLimitResponse, "Success")) {
            reviewItemErrorLabel.setText(postLimitResponse);
            return;
        }
        if (item.getPoster().equalsIgnoreCase(Client.getMyUser().getUsername())) {
            reviewItemErrorLabel.setText("You cannot review your own item.");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String review = reviewTextArea.getText();
        String quality;
        SingleSelectionModel<String> selectedModel = qualityComboBox.getSelectionModel();
        if ((quality = selectedModel.getSelectedItem()) == null || Objects.equals(quality = selectedModel.getSelectedItem(), "")) {
            reviewItemErrorLabel.setText("You need to select a quality before posting your review");
            return;
        }
        if (review.isEmpty()) {
            reviewItemErrorLabel.setText("You need to add a description to your review before posting your review");
            return;
        }
        stringBuilder.append(review).append(" - ").append(quality);
        reviewList.add(stringBuilder.toString());
        reviewTextArea.setText("");
        reviewItemErrorLabel.setText("You have successfully posted a review!");
        listingService.postReview(new Review(item.getKey(), stringBuilder.toString(), Client.getMyUser().getUsername(), quality, new Timestamp(System.currentTimeMillis())));
        selectItem(item);
    }

    @FXML
    private void onAdvancedSearchClick() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Client.class.getResource("advancedsearch-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 460, 518);
            Stage stage = new Stage();
            stage.setTitle("Advanced Search");
            stage.setScene(scene);
            ((AdvancedSearchController)fxmlLoader.getController()).addCategories(categories.keySet());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public ObservableList<Item> getItemList() {
        return itemList;
    }
}

