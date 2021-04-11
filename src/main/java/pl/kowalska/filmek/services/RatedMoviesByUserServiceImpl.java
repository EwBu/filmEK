package pl.kowalska.filmek.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kowalska.filmek.dto.RatedMoviesByUser;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.model.MovieRaiting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RatedMoviesByUserServiceImpl implements RatedMoviesByUserService {

    @Autowired
    private MovieRaitingService movieRaitingService;

    @Autowired
    private MovieService movieService;

    @Override
    public List<RatedMoviesByUser> findMoviesAndRatingsByUser(Long userId) {

        List<MovieRaiting> listRatings = movieRaitingService.findRatedByUser(userId);
        List<MovieEntity> listMovies = new ArrayList<>();
        listRatings.forEach(movie -> listMovies.add(movieService.findSingleMovieInDatabase(movie.getRaitingId().getId())));

        List<RatedMoviesByUser> moviesRatedByUser = listRatings.stream().map(rating -> {
            MovieEntity ratedMovie = listMovies.stream().
                    filter(movie -> movie.getId() == rating.getRaitingId()
                            .getId())
                    .findFirst().get();
            return new RatedMoviesByUser(ratedMovie, rating);
        }).collect(Collectors.toList());
        return moviesRatedByUser;
    }
}
