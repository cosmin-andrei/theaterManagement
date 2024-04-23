package ro.iss2024.theatermanagement.repository.irepository;

import ro.iss2024.theatermanagement.domain.Spectator;
import ro.iss2024.theatermanagement.repository.IRepository;

import java.util.Optional;

public interface ISpectatorRepo extends IRepository<Long, Spectator> {

    Optional<Spectator> findByUsername(String username);

}
