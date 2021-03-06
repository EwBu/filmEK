package pl.kowalska.filmek.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {

    void save(UserDto registrationDto);

    void update(User user);

    User findUserByEmail(String email);

    User findUserByUsername(String username);

    List<User> findAll();
}
