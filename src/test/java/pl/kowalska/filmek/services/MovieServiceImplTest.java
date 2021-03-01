package pl.kowalska.filmek.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.FilmEkApplication;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.Genre;
import pl.kowalska.filmek.moviePojo.ListOfGenres;
import pl.kowalska.filmek.moviePojo.MoviesList;
import pl.kowalska.filmek.moviePojo.Result;
import pl.kowalska.filmek.repository.GenreRepository;
import pl.kowalska.filmek.repository.MovieRepository;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieServiceImplTest {

    GenreEntity genre = null;

    @Autowired
    public MovieRepository movieRepo;


    @Autowired
    public GenreRepository genreRepository;

    @BeforeEach
    void findGenre(){
       genre = genreRepository.findByName("Animacja");
    }

//    @Test
//    void findMovieByGenre(){
//        List<MovieEntity> queryMovies = movieRepo.findMoviesByGenre(genre, voteMin);
//        queryMovies.forEach(System.out::println);
//    }

    @Test
    void loadMovieToDB(){
        RestTemplate restTemplate = new RestTemplate();
        MoviesList moviesList= restTemplate.getForObject("https://api.themoviedb.org/3/movie/popular?api_key=e529d754811a8187c547ac59aa92495d&language=pl&page=1", MoviesList.class);
        List<Result> results = moviesList.getResults();
        results.forEach(movie -> {
            List<GenreEntity> genresForCurrentMovie = new ArrayList<>();
            movie.getGenreIds().forEach(genre ->genresForCurrentMovie.add(genreRepository.getOne(Long.valueOf(genre))));
            MovieEntity movieEntity = new MovieEntity(movie.getId(), movie.getPosterPath(), movie.getTitle(), movie.getOriginalTitle(), movie.getOriginalLanguage(), movie.getOverview(), movie.getPopularity(), movie.getReleaseDate(), movie.getVoteAverage(), movie.getVoteCount(), genresForCurrentMovie);// Trzeba przerobić tabele movies zeby przyjmowała to co potrzebujemy, trzeba tez przerobic encje
            if(movieEntity.getOverview() != "") {
                movieRepo.save(movieEntity);
            }

        } );
    }

    @Test
    public void loadGenresToDb(){
        RestTemplate restTemplate = new RestTemplate();
        ListOfGenres genresList= restTemplate.getForObject("https://api.themoviedb.org/3/genre/movie/list?api_key=e529d754811a8187c547ac59aa92495d&language=pl", ListOfGenres.class);
        List<Genre> genres = genresList.getGenres();

        genres.forEach(genre -> {
            GenreEntity genreEntity = new GenreEntity(Long.valueOf(genre.getId()),genre.getName());
            genreRepository.save(genreEntity);
        } );
    }


}