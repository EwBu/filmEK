package pl.kowalska.filmek;

import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.Genre;
import pl.kowalska.filmek.moviePojo.ListOfGenres;
import pl.kowalska.filmek.moviePojo.MoviesList;
import pl.kowalska.filmek.moviePojo.Result;

import java.util.ArrayList;
import java.util.List;

public class ListRefiler implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
//        @GetMapping("/loadGenres")
//        public void loadGenresToDb(){
//            RestTemplate restTemplate = new RestTemplate();
//            ListOfGenres genresList= restTemplate.getForObject("https://api.themoviedb.org/3/genre/movie/list?api_key=e529d754811a8187c547ac59aa92495d&language=pl", ListOfGenres.class);
//            List<Genre> genres = genresList.getGenres();
//
//            genres.forEach(genre -> {
//                GenreEntity genreEntity = new GenreEntity(Long.valueOf(genre.getId()),genre.getName());
//                genreRepository.save(genreEntity);
//            } );
//            System.out.println("DONE");
//        }
//
//        @GetMapping("/Movies")
//        public void loadMoviesToDb(){
//            RestTemplate restTemplate = new RestTemplate();
//            MoviesList moviesList= restTemplate.getForObject("https://api.themoviedb.org/3/movie/top_rated?api_key=e529d754811a8187c547ac59aa92495d&language=pl", MoviesList.class);
//            List<Result> results = moviesList.getResults();
//
////                                                        (Integer id, String posterPath, String polishTitle, String originalTitle, String originalLanguage, String overview, double popularity, String releaseDate, long runtime, double voteAverage, long voteCount, Set<GenreEntity> genres)
//            results.forEach(movie -> {
//                List<GenreEntity> genresForCurrentMovie = new ArrayList<>();
//                movie.getGenreIds().forEach(genre ->genresForCurrentMovie.add(genreRepository.getOne(Long.valueOf(genre))));
//                MovieEntity movieEntity = new MovieEntity(movie.getId(), movie.getPosterPath(), movie.getTitle(), movie.getOriginalTitle(), movie.getOriginalLanguage(), movie.getOverview(), movie.getPopularity(), movie.getReleaseDate(), movie.getVoteAverage(), movie.getVoteCount(), genresForCurrentMovie);// Trzeba przerobić tabele movies zeby przyjmowała to co potrzebujemy, trzeba tez przerobic encje
//                if(movieEntity.getOverview() != "") {
//                    movieRepo.save(movieEntity);
//                }
//
//            } );
//            System.out.println("DONE");
//        }
    }
}
