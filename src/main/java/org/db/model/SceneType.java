package org.db.model;

public enum SceneType {
    LOGIN("login-view.fxml"),
    REGISTER("register-view.fxml"),
    HOME_PAGE("homepage-view.fxml")
    ;

    private final String fileName;

    SceneType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
