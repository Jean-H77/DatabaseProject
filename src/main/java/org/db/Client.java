package org.db;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.controller.Controller;
import org.db.controller.Navigator;
import org.db.database.Database;
import org.db.database.DatabaseType;
import org.db.database.Datasource;
import org.db.model.SceneType;
import org.db.model.User;
import org.db.service.Service;
import org.db.service.ServiceType;
import org.db.service.impl.LoginService;
import org.db.service.impl.RegistrationService;

import java.util.HashMap;

public class Client extends Application {

    private static Client INSTANCE;

    private static final HashMap<ServiceType, Service> SERVICES = new HashMap<>();
    private Stage primaryStage;
    private User myUser;

    public Client() {
        INSTANCE = this;
    }

    public static void main(String[] args) {
        Client client = new Client();
        Database database = client.setupDatabase();
        setupServices(database);
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("App");
        primaryStage = stage;
        Navigator.switchScene(SceneType.LOGIN);
    }

    public void switchScene(Scene scene, Controller controller) {
        controller.destory();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private Database setupDatabase() {
        Datasource.init();
        return DatabaseType.MYSQL.getDatabase();
    }

    private static void setupServices(Database database) {
        SERVICES.put(ServiceType.LOGIN, new LoginService(database));
        SERVICES.put(ServiceType.REGISTRATION, new RegistrationService(database));
    }

    public static Service getService(ServiceType serviceType) {
        return SERVICES.get(serviceType);
    }

    public static Client getInstance() {
        return INSTANCE;
    }
}