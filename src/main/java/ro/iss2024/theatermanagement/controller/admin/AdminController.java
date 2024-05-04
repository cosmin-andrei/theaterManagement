package ro.iss2024.theatermanagement.controller.admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import ro.iss2024.theatermanagement.controller.utils.MessageAlert;
import ro.iss2024.theatermanagement.domain.Performance;
import ro.iss2024.theatermanagement.observer.Observer;
import ro.iss2024.theatermanagement.service.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class AdminController implements Observer {
    private Service service;

    @FXML
    private TableView<Performance> tblPerformances;
    @FXML
    private TableColumn<Performance, String> colTitle;
    @FXML
    private TableColumn<Performance, Integer> colDuration;
    @FXML
    private TableColumn<Performance, String> colDate;

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtDuration;
    @FXML
    private TextField txtDate;

    private ObservableList<Performance> model = FXCollections.observableArrayList();

    public void setService(Service service) throws SQLException {
        this.service = service;
        service.registerObserver(this);
        initModel();
        initialize();
        playOfDay();
    }

    private void playOfDay() throws SQLException {
        txtTitle.disableProperty().setValue(true);
        txtDuration.disableProperty().setValue(true);
        txtDate.disableProperty().setValue(true);

        Performance performance = service.getPlayOfDay();
        if(performance!= null){
            txtTitle.setText(performance.getTitlePlay());
            txtDuration.setText(String.valueOf(performance.getDurationPlay()));
            txtDate.setText(performance.getDate().toString());
        }else{
            txtTitle.setText("No play of the day");
        }

    }


    @Override
    public void update() throws SQLException {
        initModel();
    }

    private void initModel() throws SQLException {
        List<Performance> performanceList = service.getAllPerformances();
        model.setAll(performanceList);
    }

    @FXML
    public void initialize() {
        colTitle.setCellValueFactory(new PropertyValueFactory<>("titlePlay"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("durationPlay"));
        colDate.setCellValueFactory(cellData -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = cellData.getValue().getDate();
            return new SimpleStringProperty(dateFormat.format(timestamp));
        });

        tblPerformances.setItems(model);
    }


    public void handleAdd(ActionEvent actionEvent) {
        showEditDialog(null);
    }

    private void showEditDialog(Performance performance) {

        try {
            FXMLLoader editLoader = new FXMLLoader();
            editLoader.setLocation(getClass().getResource("/edit-view.fxml"));

            Stage editStage = new Stage();
            Scene adminScene = new Scene(editLoader.load());

            editStage.setTitle("Editeaza un performance");
            editStage.setScene(adminScene);

            EditController editController = editLoader.getController();
            editController.setService(service, performance, editStage);
            editStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void handleModify(ActionEvent actionEvent) {
        Performance performance = tblPerformances.getSelectionModel().getSelectedItem();
        if (performance == null) {
            MessageAlert.showErrorMessage(null, "Select a performance");
            return;
        }
        System.out.println(performance.getId());
        showEditDialog(performance);
    }

    public void handleDelete(ActionEvent actionEvent) {
        Performance performance = tblPerformances.getSelectionModel().getSelectedItem();
        if (performance != null) {
            service.deletePerformance(performance);
            MessageAlert.showMessage(null,"Performance deleted");
        } else {
            MessageAlert.showErrorMessage(null, "Select a performance");
        }
    }

    public void handleModifyPrice(ActionEvent actionEvent) {
        showModifyPriceDialog();
    }

    private void showModifyPriceDialog() {
        try {
            FXMLLoader priceLoader = new FXMLLoader();
            priceLoader.setLocation(getClass().getResource("/modifyPrice-view.fxml"));

            Stage priceStage = new Stage();
            Scene priceScene = new Scene(priceLoader.load());

            priceStage.setTitle("Editeaza preturile");
            priceStage.setScene(priceScene);

            ModifyPriceController priceController = priceLoader.getController();
            priceController.setService(service);
            priceStage.show();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
