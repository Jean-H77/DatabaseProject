package org.db.controller;

import javafx.fxml.Initializable;
import org.db.Client;
import org.db.service.Service;
import org.db.service.ServiceType;

public interface Controller extends Initializable {

    default Service getService(ServiceType serviceType) {
        return Client.getService(serviceType);
    }
    void destory();
}
