package ro.iss2024.theatermanagement.controller.utils;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MessageAlert {
    public static void showMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.CONFIRMATION);
        message.setHeaderText("Success");
        message.setContentText(text);
        message.initOwner(owner);
        message.setOnCloseRequest(event -> message.close());
        message.showAndWait();
    }

    public static void showErrorMessage(Stage owner, String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.initOwner(owner);
        message.setTitle("EROARE");
        message.setContentText(text);
        message.setOnCloseRequest(event -> message.close());
        message.showAndWait();
    }
}
