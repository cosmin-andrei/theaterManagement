package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.SeatCategory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class SeatCategoryRepo implements IRepository<Long, SeatCategory>{

    private final Connection connection;

    public SeatCategoryRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<SeatCategory> findOne(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Iterable<SeatCategory> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<SeatCategory> save(SeatCategory entity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<SeatCategory> delete(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<SeatCategory> update(SeatCategory entity) throws SQLException {
        return Optional.empty();
    }
}
