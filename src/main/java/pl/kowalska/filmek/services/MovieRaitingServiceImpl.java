package pl.kowalska.filmek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.model.MovieRaiting;
import pl.kowalska.filmek.model.MovieRaitingKey;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.repository.MovieRatingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieRaitingServiceImpl implements MovieRaitingService{

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @Autowired UserService userService;

    @Autowired MovieService movieService;

    @Override
    public void save(MovieRaiting movieRaiting) {
        movieRatingRepository.save(movieRaiting);
    }

    @Override
    public MovieRaiting findByMovieId(MovieRaitingKey movieRaitingKey) {
        return movieRatingRepository.findByCompositeKey(movieRaitingKey);
    }

    @Override
    public Optional<MovieRaiting> findRating(Long movieId) {
        Optional<User> optionalUser = userService.retrieveUserFromSecurityContext();
        if (optionalUser.isEmpty()){
            return Optional.empty();
        }
        User user = optionalUser.get();
        MovieEntity singleMovieInDatabase = movieService.findSingleMovieInDatabase(movieId);
        return Optional.ofNullable(findByMovieId(new MovieRaitingKey(user.getUserId(), singleMovieInDatabase.getId())));
    }

    @Override
    public List<MovieRaiting> findRatedByUser (Long userId){
        List<MovieRaiting> ratedMovies = movieRatingRepository.findAllRatingByFollowingUser(userId);
        return ratedMovies;
    }
}
