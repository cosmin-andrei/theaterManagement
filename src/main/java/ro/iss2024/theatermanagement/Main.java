package ro.iss2024.theatermanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ro.iss2024.theatermanagement.controller.admin.AdminController;
import ro.iss2024.theatermanagement.controller.LoginController;
import ro.iss2024.theatermanagement.controller.spectator.SpectatorController;
import ro.iss2024.theatermanagement.domain.Spectator;
import ro.iss2024.theatermanagement.repository.*;
import ro.iss2024.theatermanagement.service.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {

    final String url = "jdbc:postgresql://localhost:5432/theatermanagement";
    final String username = "postgres";
    final String password = "2003";

    private Connection connection;

    {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    AdminRepo adminRepo = new AdminRepo(connection);
    SpectatorRepo spectatorRepo = new SpectatorRepo(connection);
    PerformanceRepo performanceRepo = new PerformanceRepo(connection);
    ReservationRepo reservationRepo = new ReservationRepo(connection);
    SeatCategoryRepo seatCategoryRepo = new SeatCategoryRepo(connection);
    SeatRepo seatRepo = new SeatRepo(connection);
    SeatReserverdRepo seatReserverdRepo = new SeatReserverdRepo(connection);

    Service service = new Service(adminRepo, spectatorRepo, performanceRepo, seatCategoryRepo, reservationRepo, seatRepo, seatReserverdRepo);


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loginLoader = new FXMLLoader();
        loginLoader.setLocation(getClass().getResource("views/login-view.fxml"));

        AnchorPane loginVBox = loginLoader.load();
        LoginController loginController = loginLoader.getController();
        loginController.setMain(this);
        loginController.setService(service, stage);

        Scene scene = new Scene(loginVBox);
        stage.setTitle("Theater Management");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    public void openAdminStage() {
        try {
            FXMLLoader adminLoader = new FXMLLoader();
            adminLoader.setLocation(getClass().getResource("views/admin/admin-view.fxml"));


            Stage adminStage = new Stage();
            Scene adminScene = new Scene(adminLoader.load());

            adminStage.setTitle("Panou administrator");
            adminStage.setScene(adminScene);

            AdminController adminController = adminLoader.getController();
            adminController.setService(service);
            adminStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void openSpectatorStage(Spectator spectator) {
        try {
            FXMLLoader spectatorLoader = new FXMLLoader();
            spectatorLoader.setLocation(getClass().getResource("views/spectator/spectator-view.fxml"));


            Stage spectatorStage = new Stage();
            Scene spectatorScene = new Scene(spectatorLoader.load());

            spectatorStage.setTitle("Panou spectator: " + spectator.getName());
            spectatorStage.setScene(spectatorScene);

            SpectatorController spectatorController = spectatorLoader.getController();
            spectatorController.setService(service, spectator);
            spectatorStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}