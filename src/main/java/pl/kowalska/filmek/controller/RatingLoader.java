package pl.kowalska.filmek.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.model.MovieRaiting;
import pl.kowalska.filmek.model.MovieRaitingKey;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.repository.MovieRatingRepository;
import pl.kowalska.filmek.repository.MovieRepository;
import pl.kowalska.filmek.repository.UserRepository;

import java.util.List;
import java.util.logging.Logger;

@Component
public class RatingLoader implements CommandLineRunner {


    @Autowired
    private MovieRatingRepository  movieRatingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;


    @Override
    public void run(String... args) {
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


    }
}
