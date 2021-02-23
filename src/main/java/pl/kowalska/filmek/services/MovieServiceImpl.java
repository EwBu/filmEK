package pl.kowalska.filmek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.SearchMovies;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.MovieObject;
import pl.kowalska.filmek.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService{

    @Autowired
    MovieRepository movieRepo;

    final RestTemplate restTemplate = new RestTemplate();
    @Value("${api.key}")
    private String key;

    @Override
    public MovieEntity findSingleMovieInDatabase(Long id) {
        Optional<MovieEntity> optionalMovieFromEntity = movieRepo.findById(id);
        return optionalMovieFromEntity.get();
    }

    @Override
    public MovieObject findSingleMovieInTmdb(String id) {
        String movieDetailsUrl = String.format("https://api.themoviedb.org/3/movie/%s?api_key=%s&language=pl", id, key);
        return restTemplate.getForObject(movieDetailsUrl, MovieObject.class);
    }

    @Override
    public List<MovieEntity> selectMoviesByRaiting(Double min, Double max) {
        List<MovieEntity> selectedMovies = movieRepo.findMoviesBasedOnRating(min,max);
        return selectedMovies;
    }
}
