package pl.kowalska.filmek.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.kowalska.filmek.CustomUserDetail;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Nie znaleziono takiego użytkownika");
        }
        return new CustomUserDetail(user);
    }
}
