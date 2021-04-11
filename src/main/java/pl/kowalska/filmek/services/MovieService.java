package pl.kowalska.filmek.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.MovieObject;

import javax.xml.crypto.Data;

import java.util.List;

public interface MovieService {

    Page<MovieEntity> findPaginatedMovies(Pageable pageable);

    Page<MovieEntity> findAll(Pageable pageable);

    MovieEntity findSingleMovieInDatabase(Long id);
    
    MovieObject findSingleMovieInTmdb(String id);

    List<MovieEntity> findMoviesByQuery(String genre, Double voteMin, Double voteMax, Double popularityMin, Double popularityMax, Integer yearMin, Integer yearMax);

    Void saveMovieToDb(Long id);

    Boolean checkMovieInDb(Long id);
}
