package ro.iss2024.theatermanagement.controller.spectator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ro.iss2024.theatermanagement.controller.utils.MessageAlert;
import ro.iss2024.theatermanagement.controller.utils.SeatDTO;
import ro.iss2024.theatermanagement.domain.Performance;
import ro.iss2024.theatermanagement.domain.Reservation;
import ro.iss2024.theatermanagement.domain.Spectator;
import ro.iss2024.theatermanagement.observer.Observer;
import ro.iss2024.theatermanagement.service.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SeatsController implements Observer {

    private Spectator spectator;
    private Service service;
    private Performance performance;
    private ObservableList<SeatDTO> model = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> boxLodge;
    @FXML
    private ChoiceBox<String> boxRow;

    @FXML
    private TableView<SeatDTO> tblSeats;
    @FXML
    private TableColumn<SeatDTO, String> lblLodge;
    @FXML
    private TableColumn<SeatDTO, String> lblRow;
    @FXML
    private TableColumn<SeatDTO, String> lblSeat;
    @FXML
    private TableColumn<SeatDTO, String> lblPrice;
    @FXML
    private TableColumn<SeatDTO, String> lblStatus;


    public void setService(Service service, Spectator spectator) throws SQLException {
        this.service = service;
        this.spectator = spectator;
        this.performance = service.getPlayOfDay();
        initModel();
        initialize();

    }

    private void initialize() {
        lblLodge.setCellValueFactory(new PropertyValueFactory<>("lodge"));
        lblRow.setCellValueFactory(new PropertyValueFactory<>("row"));
        lblSeat.setCellValueFactory(new PropertyValueFactory<>("number"));
        lblPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        lblStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblSeats.setItems(model);
    }

    private void initModel() throws SQLException {
        List<SeatDTO> seats = service.getSeats(performance);
        model.setAll(seats);

        boxLodge.getItems().clear();
        boxRow.getItems().clear();

        seats.stream().map(SeatDTO::getLodge).distinct().forEach(boxLodge.getItems()::add);
        seats.stream().map(SeatDTO::getRow).distinct().forEach(row -> boxRow.getItems().add(String.valueOf(row)));
    }


    @Override
    public void update() throws SQLException {
        initModel();
    }

    public void finishReservation(ActionEvent actionEvent) throws SQLException {
        SeatDTO seat = tblSeats.getSelectionModel().getSelectedItem();
        if (seat != null) {
            if(seat.getStatus().equals("reserved")){
                MessageAlert.showErrorMessage(null, "Seat already reserved");
                return;
            }
            service.addReservation(new Reservation(spectator, performance), seat);
            MessageAlert.showMessage(null, "Reservation made");
            update();
        }
        else{
            MessageAlert.showErrorMessage(null, "No seat selected");
        }
    }

    public void handleLodgeSelect(ActionEvent dragEvent) throws SQLException {
        String lodge = boxLodge.getValue();
        if(boxRow.getValue() != null){
            handleSelect();
            return;
        }
        List<SeatDTO> newModel = new ArrayList<>();
        List<SeatDTO> seats = service.getSeats(performance);
        for (SeatDTO seat : seats) {
            if (Objects.equals(seat.getLodge(), lodge)) {
                newModel.add(seat);
            }
        }

        model.setAll(newModel);
    }

    public void handleRowSelect(ActionEvent dragEvent) throws SQLException {
        int row = Integer.parseInt(boxRow.getValue());

        if(boxLodge.getValue() != null){
            handleSelect();
            return;
        }
        List<SeatDTO> newModel = new ArrayList<>();
        List<SeatDTO> seats = service.getSeats(performance);
        for (SeatDTO seat : seats) {
            if (seat.getRow() == row) {
                newModel.add(seat);
            }
        }

        model.setAll(newModel);

    }

    public void handleSelect() throws SQLException {
        String lodge = boxLodge.getValue();
        int row = Integer.parseInt(boxRow.getValue());

        List<SeatDTO> newModel = new ArrayList<>();
        List<SeatDTO> seats = service.getSeats(performance);
        for (SeatDTO seat : seats) {
            if (Objects.equals(seat.getLodge(), lodge) && seat.getRow() == row) {
                newModel.add(seat);
            }
        }

        model.setAll(newModel);
    }
}
