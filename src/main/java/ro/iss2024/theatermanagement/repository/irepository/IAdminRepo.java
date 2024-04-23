package ro.iss2024.theatermanagement.repository.irepository;

import ro.iss2024.theatermanagement.domain.Admin;
import ro.iss2024.theatermanagement.repository.IRepository;

import java.util.Optional;

public interface IAdminRepo extends IRepository<Long, Admin> {
    Optional<Admin> findByUsername(String username);
}
