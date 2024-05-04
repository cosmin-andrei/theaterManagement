package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.Performance;
import ro.iss2024.theatermanagement.domain.SeatCategory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SeatCategoryRepo implements IRepository<Long, SeatCategory>{

    private final Connection connection;

    public SeatCategoryRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<SeatCategory> findOne(Long aLong) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("select * from seat_category where id=?");
        ) {

            statement.setLong(1, aLong);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                Long id = result.getLong("id");
                String name = result.getString("name");
                int price = result.getInt("price");
                SeatCategory seatCategory = new SeatCategory(name, price);
                seatCategory.setId(id);
                return Optional.of(seatCategory);
            }
        } catch (SQLException e) {
            System.out.println("Error DB " + e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<SeatCategory> findAll() throws SQLException {
        List<SeatCategory> seats = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("select * from seat_category");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next())
            {
                Long id= resultSet.getLong("id");
                String lodge = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                SeatCategory seat = new SeatCategory(lodge, price);
                seat.setId(id);
                seats.add(seat);

            }
            return seats;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
        if (entity == null) {
            throw new IllegalArgumentException("Eroare, performance null");
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE seat_category SET name = ?, price = ? WHERE id = ?"
        )) {
            statement.setString(1, entity.getName());
            statement.setDouble(2, entity.getPrice());
            statement.setLong(3, entity.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return Optional.empty();

    }
}
