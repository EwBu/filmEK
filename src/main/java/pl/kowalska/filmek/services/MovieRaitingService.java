package pl.kowalska.filmek.services;

import org.springframework.stereotype.Service;
import pl.kowalska.filmek.model.MovieRaiting;
import pl.kowalska.filmek.model.MovieRaitingKey;

@Service
public interface MovieRaitingService {

    void save(MovieRaiting movieRaiting);

    MovieRaiting findByMovieId(MovieRaitingKey movieRaitingKey);
}
