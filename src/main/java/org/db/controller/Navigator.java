package org.db.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.db.Application;
import org.db.model.SceneType;

import java.util.HashMap;

public class Navigator {

    public static HashMap<SceneType, Scene> cachedScenes = new HashMap<>();

    public static void switchScene(SceneType sceneType) {
        Scene scene;
        if((scene = cachedScenes.get(sceneType)) != null)
            Application.updateScene(scene);
        else {
            String fileName = SceneType.VALUES.get(sceneType);
            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource(fileName));
            try {
                scene = new Scene(fxmlLoader.load(), 388, 449);
                cachedScenes.put(sceneType, scene);
                Application.updateScene(scene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
