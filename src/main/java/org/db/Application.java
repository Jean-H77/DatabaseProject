package org.db;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.database.Database;
import org.db.database.DatabaseType;
import org.db.database.Datasource;
import org.db.service.Service;
import org.db.service.ServiceType;
import org.db.service.impl.LoginService;
import org.db.service.impl.RegistrationService;

import java.util.HashMap;

public class Application extends javafx.application.Application {

    private static final HashMap<ServiceType, Service> SERVICES = new HashMap<>();

    public static void main(String[] args) {
        // init database
        Database database = setupDatabase(DatabaseType.MYSQL);
        // setup services
        setupServices(database);
        // launch gui
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 388, 449); // modified window size
        primaryStage.setTitle("App");
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