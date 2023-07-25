package org.db.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.db.Application;

public class Navigator {
    public static void switchScene(String screen) {
    String sceneFile;
        switch(screen) {
            case "Login":
                sceneFile = "login-view.fxml";
                break;
            case "LoggedIn":
                sceneFile = "logged-in-view.fxml";
                break;
            case "Register":
                sceneFile = "register-view.fxml";
                break;
            default:
                sceneFile = "no file";
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(sceneFile));
            Scene scene = new Scene(fxmlLoader.load(), 388, 449);
            Application.updateScene(scene);
        }
        catch(Exception e) {
            System.out.println("Error switching scenes.");
        }
    }
}
