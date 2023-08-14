package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.db.controller.Controller;
import org.db.controller.Navigator;
import org.db.model.Item;
import org.db.model.SceneType;
import org.db.model.User;
import org.db.service.ServiceType;
import org.db.service.impl.AdvancedSearchService;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AdvancedSearchController implements Controller {

    @FXML
    private ComboBox<String> searchMostExpensiveCategoryComboBox;

    @FXML
    private ComboBox<String> searchUserDateCat1ComboBox;

    @FXML
    private ComboBox<String> searchUserDateCat2ComboBox;

    @FXML
    private ComboBox<String> selectReviewTypeComboBox;

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

    @FXML
    private void searchMostExpensiveCategoryComboBox() {

    }

    @Override
    public void destory() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void searchMostExpensiveOnSearchButtonClick() {
        if(searchMostExpensiveCategoryComboBox.getValue() != null || !Objects.equals(searchMostExpensiveCategoryComboBox.getValue(), "")) {
            String category = searchMostExpensiveCategoryComboBox.getValue();
            List<Item> result = advancedSearchService.searchMostExpensiveItemsByCategory(category);
            ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).getItemList().setAll(result);
        }
    }

    @FXML
    private void searchUserDateCatOnSearchButtonClick() {
        if(
        (searchUserDateCat1ComboBox.getValue() != null
        || !Objects.equals(searchUserDateCat1ComboBox.getValue(), ""))
        && (searchUserDateCat2ComboBox.getValue() != null
        || !Objects.equals(searchUserDateCat2ComboBox.getValue(), ""))
        ) {
            String cat1 = searchUserDateCat1ComboBox.getValue();
            String cat2 = searchUserDateCat2ComboBox.getValue();
            List<Item> result = advancedSearchService.searchUsersByDateAndCategory(cat1, cat2);
            ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).getItemList().setAll(result);
        }
    }

    @FXML
    private void selectReviewTypeSearch(){
        String reviewType = selectReviewTypeComboBox.getValue();
        List<Item> itemsWithReview = advancedSearchService.searchItemsByReviewQualityType(reviewType);
        ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).getItemList().setAll(itemsWithReview);
    }

}
