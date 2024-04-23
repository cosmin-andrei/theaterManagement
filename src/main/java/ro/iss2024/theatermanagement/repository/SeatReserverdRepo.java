package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.SeatReserved;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class SeatReserverdRepo implements IRepository<Long, SeatReserved>{

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
        return null;
    }

    @Override
    public Optional<SeatReserved> save(SeatReserved entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Eroare, performance null");
        }


        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO seatReserved(id, id_seat, id_reservation) VALUES (?, ?, ?)"
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
