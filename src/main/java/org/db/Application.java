package org.db;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.controller.Navigator;
import org.db.database.Database;
import org.db.database.DatabaseType;
import org.db.database.Datasource;
import org.db.model.SceneType;
import org.db.service.Service;
import org.db.service.ServiceType;
import org.db.service.impl.LoginService;
import org.db.service.impl.RegistrationService;

import java.util.HashMap;

public class Application extends javafx.application.Application {

    private static final HashMap<ServiceType, Service> SERVICES = new HashMap<>();
    private static Stage primaryStage;

    public static void main(String[] args) {
        // init database
        Database database = setupDatabase(DatabaseType.MYSQL);
        // setup services
        setupServices(database);
        // launch gui
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("App");
        primaryStage = stage;
        Navigator.switchScene(SceneType.LOGIN);
    }

    public static void updateScene(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Database setupDatabase(DatabaseType databaseType) {
        // initialize the database
        Datasource.init();
        // return the chosen database
        return databaseType.getDatabase();
    }

    public static void setupServices(Database database) {
        // use the given database for the services
        SERVICES.put(ServiceType.LOGIN, new LoginService(database));
        SERVICES.put(ServiceType.REGISTRATION, new RegistrationService(database));
    }

    public static Service getService(ServiceType serviceType) {
        return SERVICES.get(serviceType);
    }
}