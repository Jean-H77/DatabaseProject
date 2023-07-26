package org.db.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SceneType {
    LOGIN("login-view.fxml"),
    REGISTER("register-view.fxml"),
    HOME_PAGE("logged-in-view.fxml")
    ;

    private final String fileName;

    SceneType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
