module ro.iss2024.theatermanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires java.sql;

    opens ro.iss2024.theatermanagement to javafx.fxml;
    opens ro.iss2024.theatermanagement.controller to javafx.fxml;
    opens ro.iss2024.theatermanagement.domain to javafx.fxml;
    opens ro.iss2024.theatermanagement.repository to javafx.fxml;
    opens ro.iss2024.theatermanagement.service to javafx.fxml;
    opens ro.iss2024.theatermanagement.controller.utils to javafx.fxml;
    opens ro.iss2024.theatermanagement.controller.admin to javafx.fxml;
    opens ro.iss2024.theatermanagement.controller.spectator to javafx.fxml;

    exports ro.iss2024.theatermanagement;
    exports ro.iss2024.theatermanagement.controller;
    exports ro.iss2024.theatermanagement.domain;
    exports ro.iss2024.theatermanagement.repository;
    exports ro.iss2024.theatermanagement.service;
    exports ro.iss2024.theatermanagement.controller.utils;
    exports ro.iss2024.theatermanagement.controller.admin;
    exports ro.iss2024.theatermanagement.controller.spectator;

}