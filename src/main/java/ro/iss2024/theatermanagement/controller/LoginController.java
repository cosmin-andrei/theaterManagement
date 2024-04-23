package ro.iss2024.theatermanagement.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import ro.iss2024.theatermanagement.Main;
import ro.iss2024.theatermanagement.controller.utils.MessageAlert;
import ro.iss2024.theatermanagement.domain.User;
import ro.iss2024.theatermanagement.service.Service;

import java.sql.SQLException;

public class LoginController {

    private Service service;
    private Stage stage;
    private Main main;

    @FXML
    private TextField textUsername;
    @FXML
    private TextField textPassword;

    public void setService(Service service, Stage stage) {
        this.service = service;
        this.stage = stage;
    }

    public void handleLogin() throws SQLException {
        if (textUsername.getText().isEmpty()) {
            MessageAlert.showErrorMessage(null, "Introdu un username");
        } else if (textPassword.getText().isEmpty()) {
            MessageAlert.showErrorMessage(null, "Introdu parola");
        } else {
            if (service.login_admin(new User(textUsername.getText(), "", textPassword.getText()))) {
                main.openAdminStage();
                stage.close();
            } else if (service.login_spectator(new User(textUsername.getText(), "", textPassword.getText()))) {
                try {
                    main.openSpectatorStage(service.findSpectator(textUsername.getText()));
                    stage.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    MessageAlert.showErrorMessage(null, "Eroare: " + e.getMessage());
                }
            } else {
                MessageAlert.showErrorMessage(null, "Datele de logare sunt incorecte");
            }
        }
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void handleKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().toString().equals("ENTER")) {
            try {
                handleLogin();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
