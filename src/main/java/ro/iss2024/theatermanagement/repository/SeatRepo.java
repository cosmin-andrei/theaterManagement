package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.Performance;
import ro.iss2024.theatermanagement.domain.Seat;
import ro.iss2024.theatermanagement.domain.SeatCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
        List<Seat> seats = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("select * from seat");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next())
            {
                Long id= resultSet.getLong("id");
                int row = resultSet.getInt("row");
                int number = resultSet.getInt("number");
                Long seatCategory = resultSet.getLong("seatCategory");
                SeatCategory sc = new SeatCategory("", 0);
                sc.setId(seatCategory);
                Seat seat = new Seat(row, number, sc);
                seat.setId(id);
                seats.add(seat);

            }
            return seats;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
