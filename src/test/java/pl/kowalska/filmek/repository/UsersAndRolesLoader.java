package pl.kowalska.filmek.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import pl.kowalska.filmek.model.Role;
import pl.kowalska.filmek.model.User;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersAndRolesLoader {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    @Sql({"/roles.sql"})
    public void loadRolesToDb(){
        assertEquals(3, roleRepository.findAll().size());
    }

    @Test
    public void loadUsersToDb(){
        List<Role> allRoles = roleRepository.findAll();
        List<Role> admin= allRoles.stream().filter(role -> role.getName().toLowerCase().matches("admin|user")).collect(Collectors.toList());
        List<Role> user= allRoles.stream().filter(role -> role.getName().equalsIgnoreCase("user")).collect(Collectors.toList());


        User user1 = new User("Admin",
                "admin@gmail.com",
                passwordEncoder.encode("admin"),
                'M',
                admin,true);
        User user2 = new User("Kinomaniak",
                "user1@gmail.com",
                passwordEncoder.encode("user1"),
                'M',
                user,true);
        User user3 = new User("Karolina",
                "Karolina@gmail.com",
                passwordEncoder.encode("dirtyDancing"),
                'K',
                user,true);
        User user4 = new User("Malinka",
                "IzabelaMarczak@gmail.com",
                passwordEncoder.encode("jasImalgosia"),
                'M',
                user,true);
        User user5 = new User("Zenek",
                "ZenonMartyniuk@gmail.com",
                passwordEncoder.encode("przezTweFilmyPelnometrazowe"),
                'M',
                user,true);
        User user6 = new User("KrytykFilmowy",
                "MichalOpasek@gmail.com",
                passwordEncoder.encode("FullHD4K"),
                'M',
                user,true);
        User user7 = new User("KaMyK",
                "Mateusz_B@gmail.com",
                passwordEncoder.encode("Standard8mm"),
                'M',
                user,true);
        User user8 = new User("Stokrotka",
                "Kamila96@gmail.com",
                passwordEncoder.encode("filmyKrotkometrazowe"),
                'K',
                user,true);
        User user9 = new User("Snajper",
                "RobertWydra@gmail.com",
                passwordEncoder.encode("CzeskiFilm"),
                'M',
                user,true);
        User user10 = new User("Maurycy",
                "SuperUser@gmail.com",
                passwordEncoder.encode("!Qwerty123?"),
                'M',
                user,true);

        Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10)
                .forEach(moviesappdb -> userRepository.saveAndFlush(moviesappdb));

    }


}