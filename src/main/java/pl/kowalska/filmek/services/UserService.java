package pl.kowalska.filmek.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.ConfirmationToken;
import pl.kowalska.filmek.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void save(UserDto registrationDto);

    User findUserByEmail(String email);

    User findUserById(Long userId);

    User findUserByUsername(String username);

    List<User> findAll();

    User retrieveUserFromSecurityContext();

    void handleConfirmationMailSending(UserDto userRegistrationDto);

    void updateUserActivationState(ConfirmationToken token);

    void updateUser(UserDto userdto);

    void deleteUser(Long userId);
}
