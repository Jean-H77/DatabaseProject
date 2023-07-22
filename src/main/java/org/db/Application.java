package org.db;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.db.service.Service;
import org.db.service.ServiceType;
import org.db.service.impl.LoginService;
import org.db.service.impl.RegistrationService;

import java.util.HashMap;

public class Application extends javafx.application.Application {

    private static final HashMap<ServiceType, Service> SERVICES = new HashMap<>();

    public static void main(String[] args) {
        // init datasource
        //Datasource.init();

        // add services
        SERVICES.put(ServiceType.LOGIN, new LoginService());
        SERVICES.put(ServiceType.REGISTRATION, new RegistrationService());

        //launch gui
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        primaryStage.setTitle("App");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Service getService(ServiceType serviceType) {
        return SERVICES.get(serviceType);
    }
}