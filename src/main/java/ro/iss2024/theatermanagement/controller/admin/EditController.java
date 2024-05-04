package ro.iss2024.theatermanagement.controller.admin;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ro.iss2024.theatermanagement.controller.utils.MessageAlert;
import ro.iss2024.theatermanagement.domain.Performance;
import ro.iss2024.theatermanagement.service.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class EditController {

    private Service service;
    private Performance performance;
    private Stage stage;

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextArea txtDescription;
    @FXML
    private TextField txtDate;

    public void setService(Service service, Performance performance, Stage stage) {
        this.service = service;
        this.performance = performance;
        this.stage = stage;
        initializare();
    }

    private void initializare() {
        if (performance != null) {
            txtName.setText(performance.getTitlePlay());
            txtDuration.setText(String.valueOf(performance.getDurationPlay()));
            txtDescription.setText(performance.getDescriptionPlay());
            txtDate.setText(performance.getDate().toLocalDateTime().toString());
        }
    }

    @FXML
    public void handleSave() {
        if (txtName.getText().isEmpty() || txtDuration.getText().isEmpty() || txtDescription.getText().isEmpty() || txtDate.getText().isEmpty()) {
            MessageAlert.showErrorMessage(null, "Complete all fields");
            return;
        }

        String title = txtName.getText();
        String description = txtDescription.getText();
        int duration = Integer.parseInt(txtDuration.getText());
        Timestamp date = Timestamp.valueOf(txtDate.getText());

        Performance p = new Performance(title, duration, description, date);
        if (performance != null) {
            p.setId(performance.getId());
            service.updatePerformance(p);
        } else {
            service.addPerformance(p);
        }
        stage.close();

    }


}

