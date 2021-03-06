package pl.kowalska.filmek.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kowalska.filmek.model.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}