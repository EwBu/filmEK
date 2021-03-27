package pl.kowalska.filmek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.kowalska.filmek.repository.MovieRatingRepository;
import pl.kowalska.filmek.repository.MovieRepository;
import pl.kowalska.filmek.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RatingLoaderTest {

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

  /*  @Test
    public void addRatingRelatedWithUser(){
        User user = new User();
        user.setUserName("Admin");
        user.setEmail("admin@wp.pl");
        user.setPassword("admin");
        user.setGender('M');
        userRepository.save(user);

        User userFromDb = userRepository.findByEmail("admin@wp.pl");

        MovieEntity movieEntity = movieRepository.findById((long) 458220).get();

        MovieRaiting movieRaiting = new MovieRaiting(new MovieRaitingKey(userFromDb.getUserId(), movieEntity.getId()), 5, true);

        movieRatingRepository.save(movieRaiting);

        System.out.println("done");

        List<MovieRaiting> allRatingByFollowingUser = movieRatingRepository.findAllRatingByFollowingUser(userFromDb.getUserId());

    }*/
}

