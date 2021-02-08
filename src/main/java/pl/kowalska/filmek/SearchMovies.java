package pl.kowalska.filmek;

import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.OmdbWebServiceClient;
import pl.kowalska.filmek.model.Movie;
import pl.kowalska.filmek.moviePojo.MovieObject;

import java.lang.reflect.Array;

public class SearchMovies {

    public static String SEARCH_URL = "https://api.themoviedb.org/3/movie/775996?api_key=e529d754811a8187c547ac59aa92495d";

    public static void main(String[] args) {

//        String jsonResponse = OmdbWebServiceClient.searchMovieByTitle("batman");
//        System.out.println(jsonResponse);
//        String jsonResponse2 = OmdbWebServiceClient.searchMovieByIMDB("tt0372784");
//        System.out.println(jsonResponse2);
        showMovie();
    }
    public static void showMovie(){
        RestTemplate restTemplate = new RestTemplate();
        MovieObject searchMovie = restTemplate.getForObject(SEARCH_URL, MovieObject.class);
        System.out.println(searchMovie);
    }

}
