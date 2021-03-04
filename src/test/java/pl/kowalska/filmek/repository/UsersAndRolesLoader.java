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


        User user1 = new User("Kamil95",
                "Kamil@wp.pl",
                passwordEncoder.encode("filmy"),
                'M',
                admin);
        User user2 = new User("Kinomaniak",
                "Robert87@gmail.com",
                passwordEncoder.encode("cinemaCity2021"),
                'M',
                user);
        User user3 = new User("Karolina",
                "Karolina@gmail.com",
                passwordEncoder.encode("dirtyDancing"),
                'K',
                user);
        User user4 = new User("Malinka",
                "IzabelaMarczak@gmail.com",
                passwordEncoder.encode("jasImalgosia"),
                'M',
                user);
        User user5 = new User("Zenek",
                "ZenonMartyniuk@gmail.com",
                passwordEncoder.encode("przezTweFilmyPelnometrazowe"),
                'M',
                user);
        User user6 = new User("KrytykFilmowy",
                "MichalOpasek@gmail.com",
                passwordEncoder.encode("FullHD4K"),
                'M',
                user);
        User user7 = new User("KaMyK",
                "Mateusz_B@gmail.com",
                passwordEncoder.encode("Standard8mm"),
                'M',
                user);
        User user8 = new User("Stokrotka",
                "Kamila96@gmail.com",
                passwordEncoder.encode("filmyKrotkometrazowe"),
                'K',
                user);
        User user9 = new User("Snajper",
                "RobertWydra@gmail.com",
                passwordEncoder.encode("CzeskiFilm"),
                'M',
                user);
        User user10 = new User("Maurycy",
                "SuperUser@gmail.com",
                passwordEncoder.encode("!Qwerty123?"),
                'M',
                user);

        Arrays.asList(user1,user2,user3,user4,user5,user6,user7,user8,user9,user10)
                .forEach(kinomaniak -> userRepository.saveAndFlush(kinomaniak));

    }


}