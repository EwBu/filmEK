package pl.kowalska.filmek.services;

import org.springframework.stereotype.Service;
import pl.kowalska.filmek.dto.RatedMoviesByUser;

import java.util.List;

@Service
public interface RatedMoviesByUserService {

    List<RatedMoviesByUser> findMoviesAndRatingsByUser (Long userId);

    List<RatedMoviesByUser> findRatedMovies(Long userId);

    List<RatedMoviesByUser> findtoWatchMovies(Long userId);
}
