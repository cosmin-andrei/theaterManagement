package ro.iss2024.theatermanagement.service;

import ro.iss2024.theatermanagement.controller.utils.SeatDTO;
import ro.iss2024.theatermanagement.domain.*;

import java.sql.SQLException;
import java.util.List;

public interface IService {

    boolean login_admin(User user);
    boolean login_spectator(User user);
    Spectator findSpectator(String username);

    ///Administrator
    void addPerformance(Performance performance);
    void updatePerformance(Performance performance);
    void deletePerformance(Performance performance);

    void updateSeatCategory(SeatCategory seatCategory);


    ///Spectator
    List<SeatDTO> getSeats(Performance performance) throws SQLException;
    void addReservation(Reservation reservation, SeatDTO seat);

    List<Performance> getAllPerformances() throws SQLException;

    Performance getPlayOfDay() throws SQLException;
    Seat getSeat(SeatDTO seatDTO) throws SQLException;
}
