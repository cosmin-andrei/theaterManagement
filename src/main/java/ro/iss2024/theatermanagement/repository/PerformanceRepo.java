package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.Performance;
import ro.iss2024.theatermanagement.domain.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PerformanceRepo implements IRepository<Long, Performance>{

    private Connection connection;

    public PerformanceRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Performance> findOne(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Iterable<Performance> findAll() throws SQLException {
        List<Performance> performances = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement("select * from performance");
             ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next())
            {
                Long id= resultSet.getLong("id");
                String title = resultSet.getString("title");
                int duration = resultSet.getInt("duration");
                String description = resultSet.getString("description");
                Timestamp date = resultSet.getTimestamp("date");
                Performance performance = new Performance(title, duration, description, date);
                performance.setId(id);
                performances.add(performance);

            }
            return performances;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Performance> save(Performance entity) throws SQLException {

        if (entity == null) {
            throw new IllegalArgumentException("Eroare, performance null");
        }


        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO performance(id, title, duration, description, date) VALUES (?, ?, ?, ?, ?)"
        )) {
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getTitlePlay());
            statement.setInt(3, entity.getDurationPlay());
            statement.setString(4, entity.getDescriptionPlay());
            statement.setTimestamp(5, entity.getDate());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Performance> delete(Long aLong) throws SQLException {
//        if(findOne(aLong).isEmpty())
//            throw new IllegalArgumentException("ID inexistent");

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM performance WHERE id =" + aLong);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Performance> update(Performance entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Eroare, performance null");
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "UPDATE performance SET title = ?, duration=?, description=?,date=? WHERE id = ?"
        )) {
            statement.setString(1, entity.getTitlePlay());
            statement.setInt(2, entity.getDurationPlay());
            statement.setString(3, entity.getDescriptionPlay());
            statement.setTimestamp(4, entity.getDate());
            statement.setLong(5, entity.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return Optional.empty();

    }
}
