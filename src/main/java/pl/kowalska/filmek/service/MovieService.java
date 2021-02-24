package pl.kowalska.filmek.service;

import org.springframework.stereotype.Service;
import pl.kowalska.filmek.model.MovieEntity;

import java.util.List;

@Service
public class MovieService {

    public MovieEntity findMovieById(Long movieId){
        MovieEntity movie = new MovieEntity();
        System.out.println("metoda find Movie By Id");
        return movie;
    }

}
