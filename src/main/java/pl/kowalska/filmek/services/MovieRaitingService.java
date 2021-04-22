package pl.kowalska.filmek.services;

import org.springframework.stereotype.Service;
import pl.kowalska.filmek.model.MovieRaiting;
import pl.kowalska.filmek.model.MovieRaitingKey;

import java.util.List;
import java.util.Optional;

@Service
public interface MovieRaitingService {

    void save(MovieRaiting movieRaiting);

    MovieRaiting findByMovieId(MovieRaitingKey movieRaitingKey);

    Optional<MovieRaiting> findRating(Long movieId);

    List<MovieRaiting> findRatedByUser (Long userId);
}
