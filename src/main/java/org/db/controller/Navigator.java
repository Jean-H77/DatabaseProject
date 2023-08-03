package org.db.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import org.db.Client;
import org.db.controller.impl.HomepageController;
import org.db.model.SceneType;

import java.util.HashMap;

public class Navigator {

    public static HashMap<SceneType, Scene> cachedScenes = new HashMap<>();

    public static HashMap<SceneType, Controller> cachedControllers = new HashMap<>();

    public static void switchScene(SceneType sceneType) {
        Scene scene;
        Controller controller;
        if((scene = cachedScenes.get(sceneType)) != null && (controller = cachedControllers.get(sceneType)) != null)
            Client.getInstance().switchScene(scene, controller);
        else {
            String fileName = sceneType.getFileName();
            FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource(fileName));
            try {
                Parent parent = fxmlLoader.load();
                Region region = (Region) parent;
                scene = new Scene(parent, region.getPrefWidth(), region.getPrefHeight());
                controller = fxmlLoader.getController();
                cachedScenes.put(sceneType, scene);
                cachedControllers.put(sceneType, controller);
                Client.getInstance().switchScene(scene, controller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
