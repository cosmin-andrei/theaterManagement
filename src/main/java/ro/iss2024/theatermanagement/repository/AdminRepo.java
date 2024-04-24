package ro.iss2024.theatermanagement.repository;

import ro.iss2024.theatermanagement.domain.Admin;
import ro.iss2024.theatermanagement.repository.irepository.IAdminRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AdminRepo implements IAdminRepo{
    private final Connection connection;

    public AdminRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Admin> findOne(Long aLong) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Iterable<Admin> findAll() throws SQLException {
        return null;
    }

    @Override
    public Optional<Admin> save(Admin entity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Admin> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Admin> update(Admin entity) throws SQLException {
        return Optional.empty();
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        try (PreparedStatement statement = connection.prepareStatement("select * from admin " +
                "where username = ?")

        ) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String username1 = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                Admin u = new Admin(username, email, password);
                u.setId(id);
                return Optional.of(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
