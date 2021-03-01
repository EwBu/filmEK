package pl.kowalska.filmek.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kowalska.filmek.dto.UserRegistrationDto;
import pl.kowalska.filmek.model.User;

public interface UserService extends UserDetailsService {

    User save(UserRegistrationDto registrationDto);
}
