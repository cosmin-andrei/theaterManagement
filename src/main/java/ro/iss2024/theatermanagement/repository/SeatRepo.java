package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.Seat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class SeatRepo implements IRepository<Long, Seat>{

    private final Connection connection;

    public SeatRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Seat> findOne(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Iterable<Seat> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<Seat> save(Seat entity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Seat> delete(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Seat> update(Seat entity) throws SQLException {
        return Optional.empty();
    }
}
