package org.db.controller.impl;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import org.db.controller.Controller;
import org.db.controller.Navigator;
import org.db.model.Item;
import org.db.model.SceneType;
import org.db.service.ServiceType;
import org.db.service.impl.AdvancedSearchService;

import java.net.URL;
import java.util.*;

public class AdvancedSearchController implements Controller {

    private final ObservableList<String> usernameObservableList = FXCollections.observableArrayList();

    private final ObservableList<String> usernameObservableList2 = FXCollections.observableArrayList();

    @FXML
    private ComboBox<String> searchMostExpensiveCategoryComboBox;

    @FXML
    private ComboBox<String> searchUserDateCat1ComboBox;

    @FXML
    private ComboBox<String> searchUserDateCat2ComboBox;

    @FXML
    private DatePicker userMostPostsDP;

    @FXML
    private ComboBox<String> selectReviewTypeComboBox;

    @FXML
    private ComboBox<String> selectUserComboBox;

    @FXML
    private ListView<String> usernameListView;

    @FXML
    private ListView<String>  filteredUserNameListView;

    private final AdvancedSearchService advancedSearchService = (AdvancedSearchService) getService(ServiceType.ADVANCED_SEARCH);

    public void addCategories(Set<String> cats) {
        searchMostExpensiveCategoryComboBox.getItems().addAll(cats);
        searchMostExpensiveCategoryComboBox.getItems().add("All");
        searchUserDateCat1ComboBox.getItems().addAll(cats);
        searchUserDateCat2ComboBox.getItems().addAll(cats);
    }
    public void addRatingTypes(Set<String> ratingTypes) {
        selectReviewTypeComboBox.getItems().addAll(ratingTypes);
    }

    @Override
    public void destory() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        populateUsersComboBoxSelection();
        usernameListView.setItems(usernameObservableList);
        filteredUserNameListView.setItems(usernameObservableList2);
    }

    @FXML
    private void searchMostExpensiveOnSearchButtonClick() {
        if(searchMostExpensiveCategoryComboBox.getValue() != null || !Objects.equals(searchMostExpensiveCategoryComboBox.getValue(), "")) {
            String category = searchMostExpensiveCategoryComboBox.getValue();
            advancedSearchService.searchMostExpensiveItemsByCategory(category).thenAccept(items -> Platform.runLater(() -> ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).getItemList().setAll(items)));
        }
    }

    @FXML
    private void searchUserDateCatOnSearchButtonClick() {
        if(
        (searchUserDateCat1ComboBox.getValue() != null
        || !Objects.equals(searchUserDateCat1ComboBox.getValue(), ""))
        && (searchUserDateCat2ComboBox.getValue() != null
        || !Objects.equals(searchUserDateCat2ComboBox.getValue(), ""))
        && (!searchUserDateCat1ComboBox.getValue().equals(searchUserDateCat2ComboBox.getValue()))
        ) {
            String cat1 = searchUserDateCat1ComboBox.getValue();
            String cat2 = searchUserDateCat2ComboBox.getValue();
            advancedSearchService.searchUsersByDateAndCategory(cat1, cat2).thenAccept(items -> ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).getItemList().setAll(items));
        }
    }

    @FXML
    private void searchUserMostPostsOnButtonClick() {
        if(userMostPostsDP.getValue() != null) {
            int year = userMostPostsDP.getValue().getYear();
            int month = userMostPostsDP.getValue().getMonthValue();
            int date = userMostPostsDP.getValue().getDayOfMonth();
            advancedSearchService.searchUsersByMostPosts(year, month, date).thenAccept(items -> ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).getItemList().setAll(items));
        }
    }

    @FXML
    private void selectReviewTypeSearch(){
        String reviewType = selectReviewTypeComboBox.getValue();
        advancedSearchService.searchItemsByReviewQualityType(reviewType).thenAccept(items -> ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).getItemList().setAll(items));
        advancedSearchService.joinUserByReviewQualityType(reviewType).thenAccept(usernameObservableList::setAll);
    }

    @FXML
    private void filterPoorAndNullOnSearchButtonClick(){
        advancedSearchService.getUsersWithPoorOrNoReviews().thenAccept(usernameObservableList2::setAll);
    }

    @FXML
    private void filterItemsGivenCommentsOnSearchButtonClick(){
        String selectedUser = selectUserComboBox.getValue();
        advancedSearchService.filterPositiveCommentsOnSearchButtonClick(selectedUser).thenAccept(items -> ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).getItemList().setAll(items));
    }

    private void populateUsersComboBoxSelection(){
        Set ratings = new LinkedHashSet();
        String [] quality = {"Excellent", "Good", "Fair", "Poor"};
        for(int i = 0 ;i < quality.length; i++){
            ratings.add(quality[i]);
        }
        addRatingTypes(ratings);
        advancedSearchService.getAllUserNames().thenAccept(usernames -> selectUserComboBox.getItems().addAll(usernames));
    }


}
