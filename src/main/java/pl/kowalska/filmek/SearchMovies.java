package pl.kowalska.filmek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.*;
import pl.kowalska.filmek.repository.MovieRepository;
import pl.kowalska.filmek.services.MovieService;

import java.util.List;

public class SearchMovies {

    public static String SEARCH_URL = "https://api.themoviedb.org/3/movie/775996?api_key=e529d754811a8187c547ac59aa92495d";
    public static String POPULAR_MOVIES = "https://api.themoviedb.org/3/movie/popular?api_key=e529d754811a8187c547ac59aa92495d&language=pl";

    @Autowired
    static MovieService movieService;

    public static void main(String[] args) {
        showComedy();
    }
    public static void showMovie(){
        RestTemplate restTemplate = new RestTemplate();
        MovieObject searchMovie = restTemplate.getForObject(POPULAR_MOVIES, MovieObject.class);
        System.out.println(searchMovie);
    }

    public static void showMoviesByPopular(){
        RestTemplate restTemplate = new RestTemplate();
        MoviesList movies = restTemplate.getForObject(POPULAR_MOVIES, MoviesList.class);
        List<Result> results = movies.getResults();

    }

    public static void showGenres(){
        RestTemplate restTemplate = new RestTemplate();
        ListOfGenres genresList= restTemplate.getForObject("https://api.themoviedb.org/3/genre/movie/list?api_key=e529d754811a8187c547ac59aa92495d&language=pl", ListOfGenres.class);
        List<Genre> genres = genresList.getGenres();

    }

    public static  void showComedy(){
        List<MovieEntity> result = movieService.findMoviesByQuery("Komedia");
        System.out.println(result.toString());
    }

}
