package org.db.controller.impl;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.db.controller.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class AdvancedSearchController implements Controller {

    @FXML
    private ComboBox<String> searchMostExpensiveCategoryComboBox;

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

    }
}
