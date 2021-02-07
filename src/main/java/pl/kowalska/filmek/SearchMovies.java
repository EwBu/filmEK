package pl.kowalska.filmek;

import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.OmdbWebServiceClient;
import pl.kowalska.filmek.model.Movie;

import java.lang.reflect.Array;

public class SearchMovies {

    private static String SEARCH_URL = "https://api.themoviedb.org/3/movie/775996?api_key=e529d754811a8187c547ac59aa92495d";

    public static void main(String[] args) {

//        String jsonResponse = OmdbWebServiceClient.searchMovieByTitle("batman");
//        System.out.println(jsonResponse);
//        String jsonResponse2 = OmdbWebServiceClient.searchMovieByIMDB("tt0372784");
//        System.out.println(jsonResponse2);
    }
    public static String showMovie(){
        RestTemplate restTemplate = new RestTemplate();
        Movie searchMovie = restTemplate.getForObject(SEARCH_URL, Movie.class);

        return searchMovie.toString();
    }

}
