package pl.kowalska.filmek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.ConfirmationToken;
import pl.kowalska.filmek.model.Role;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.repository.ConfirmationTokenRepository;
import pl.kowalska.filmek.repository.RoleRepository;
import pl.kowalska.filmek.repository.UserRepository;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    @Value("${spring.mail.username}")
    String mailFrom;

    @Resource
    private JavaMailSender javaMailSender;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ConfirmationTokenRepository confirmationTokenRepository) {
        super();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Override
    public void save(UserDto registrationDto) {
        Role role = roleRepository.findRoleByName("USER");
        User user = new User(registrationDto.getUserName(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()), registrationDto.getGender(), Collections.singletonList(role), false);
        userRepository.save(user);
    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserById(Long userId){
        return userRepository.findByUserId(userId);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void handleConfirmationMailSending(UserDto userRegistrationDto) {
        save(userRegistrationDto);
        User user = findUserByUsername(userRegistrationDto.getUserName());

        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        MimeMessage mailMessage = javaMailSender.createMimeMessage();
        try {
            mailMessage.setSubject("Dokończ rejestrację!", "UTF-8");
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setFrom("serwis.filmek@gmail.com");
            helper.setText("Aby potwierdzić swoje konto kliknij przycisk poniżej: "
                    +"<br><a href=\""+String.format("http://localhost:8080/confirm-account?token=%s\"",confirmationToken.getConfirmationToken())+"><button>"+
                    "AKTYWUJ KONTO"+"</button></a>",true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mailMessage);
    }

    @Override
    public void updateUserActivationState(ConfirmationToken token) {
        User user = findUserByEmail(token.getUser().getEmail());
        user.setConfirmed(true);
        userRepository.save(user);
        confirmationTokenRepository.delete(token);
    }

    @Override
    public void updateUser(UserDto userDto) {
        User user = this.findUserById(userDto.getUserId());

        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        if(userDto.getPassword() != "") user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        System.out.println(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = findUserById(userId);
        user.getRoles().removeAll(user.getRoles());
        userRepository.deleteByUserId(userId);

    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }


    @Override
    public User retrieveUserFromSecurityContext() {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmail(userEmail);
    }


}