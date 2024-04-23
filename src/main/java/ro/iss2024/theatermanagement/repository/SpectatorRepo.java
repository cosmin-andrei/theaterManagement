package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.Admin;
import ro.iss2024.theatermanagement.domain.Spectator;
import ro.iss2024.theatermanagement.repository.irepository.ISpectatorRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SpectatorRepo implements ISpectatorRepo {

    private final Connection connection;

    public SpectatorRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Spectator> findOne(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Iterable<Spectator> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<Spectator> save(Spectator entity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Spectator> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Spectator> update(Spectator entity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Spectator> findByUsername(String username) {

        try (PreparedStatement statement = connection.prepareStatement("select * from spectator " +
                "where username = ?")

        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Spectator u = new Spectator(username, email, password, name);
                u.setId(id);
                return Optional.of(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
