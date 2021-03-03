package pl.kowalska.filmek.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    User save(UserDto registrationDto);

    User loadUserByEmail(String email);

    List<User> findAll();
}
