package ro.iss2024.theatermanagement.controller.spectator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ro.iss2024.theatermanagement.controller.utils.MessageAlert;
import ro.iss2024.theatermanagement.domain.Performance;
import ro.iss2024.theatermanagement.domain.Spectator;
import ro.iss2024.theatermanagement.service.Service;

import java.sql.SQLException;

public class SpectatorController {
    private Service service;
    private Spectator spectator;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDate;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextArea txtDescription;

    private Performance performance;

    public void setService(Service service, Spectator spectator) throws SQLException {
        this.service = service;
        this.spectator = spectator;
        performance = service.getPlayOfDay();
        initial();
    }

    private void initial() {

        txtName.disableProperty().setValue(true);
        txtDate.disableProperty().setValue(true);
        txtDuration.disableProperty().setValue(true);
        txtDescription.disableProperty().setValue(true);

       if (performance!= null) {
           txtName.setText(performance.getTitlePlay());
           txtDate.setText(performance.getDate().toString());
           txtDuration.setText(String.valueOf(performance.getDurationPlay()));
           txtDescription.setText(performance.getDescriptionPlay());
       }else{
              txtName.setText("No play of the day");
              MessageAlert.showMessage(null, null, "No play","No play of the day");
       }
    }

    public void handleReservation(ActionEvent actionEvent) {

    }
}
