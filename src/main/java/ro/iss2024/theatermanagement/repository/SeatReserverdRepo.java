package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatReserverdRepo implements IRepository<Long, SeatReserved> {

    private Connection connection;

    public SeatReserverdRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<SeatReserved> findOne(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Iterable<SeatReserved> findAll() throws SQLException {
        List<SeatReserved> seats = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("select * from seat_reserved");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long id_seat = resultSet.getLong("id_seat");
                Long id_reservation = resultSet.getLong("id_reservation");
                Seat seat = new Seat(0, 0, null);
                seat.setId(id_seat);
                Reservation reservation = new Reservation(new Spectator("", "", "", ""), new Performance("", 0, "", null));
                reservation.setId(id_reservation);
                SeatReserved seatR = new SeatReserved(seat, reservation);
                seatR.setId(id);
                seats.add(seatR);

            }
            return seats;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<SeatReserved> save(SeatReserved entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Eroare, performance null");
        }


        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO seat_reserved(id, id_seat, id_reservation) VALUES (?, ?, ?)"
        )) {
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getSeat().getId());
            statement.setLong(3, entity.getReservation().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<SeatReserved> delete(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<SeatReserved> update(SeatReserved entity) throws SQLException {
        return Optional.empty();
    }
}
