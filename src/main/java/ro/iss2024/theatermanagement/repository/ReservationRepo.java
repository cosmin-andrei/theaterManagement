package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.Performance;
import ro.iss2024.theatermanagement.domain.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationRepo implements IRepository<Long, Reservation>{

    private final Connection connection;

    public ReservationRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Reservation> findOne(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Iterable<Reservation> findAll() throws SQLException {
       return null;
    }

    @Override
    public Optional<Reservation> save(Reservation entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Eroare, performance null");
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO reservation(id, id_spectator, id_performance) VALUES (?, ?, ?)"
        )) {
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getSpectator().getId());
            statement.setLong(3, entity.getPerformance().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Reservation> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Reservation> update(Reservation entity) throws SQLException {
        return Optional.empty();
    }
}
