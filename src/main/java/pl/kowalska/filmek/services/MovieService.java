package pl.kowalska.filmek.services;

import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.MovieObject;

public interface MovieService {
    MovieEntity findSingleMovieInDatabase(Long id);
    
    MovieObject findSingleMovieInTmdb(String id);
}
