package pl.kowalska.filmek.services;

import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.MovieObject;

import javax.xml.crypto.Data;
import java.util.List;

public interface MovieService {
    MovieEntity findSingleMovieInDatabase(Long id);
    
    MovieObject findSingleMovieInTmdb(String id);

    List<MovieEntity> selectMoviesByRaiting(Double min, Double max);
}
