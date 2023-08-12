package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.db.controller.Controller;
import org.db.controller.Navigator;
import org.db.model.Item;
import org.db.model.SceneType;
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

    private final AdvancedSearchService advancedSearchService = (AdvancedSearchService) getService(ServiceType.ADVANCED_SEARCH);

    public void addCategories(Set<String> cats) {
        searchMostExpensiveCategoryComboBox.getItems().addAll(cats);
        searchMostExpensiveCategoryComboBox.getItems().add("All");
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
            String category = searchMostExpensiveCategoryComboBox.getValue().replace("All", "*");
            List<Item> result = advancedSearchService.searchMostExpensiveItemsByCategory(category);
            ((HomepageController) Navigator.cachedControllers.get(SceneType.HOME_PAGE)).getItemList().setAll(result);
        }
    }
}
