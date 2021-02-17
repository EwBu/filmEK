package pl.kowalska.filmek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.SearchMovies;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.Genre;
import pl.kowalska.filmek.moviePojo.MovieObject;
import pl.kowalska.filmek.repository.GenreRepository;
import pl.kowalska.filmek.repository.MovieRepository;
import pl.kowalska.filmek.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private GenreRepository genreRepository;


    @GetMapping("/{movieId}")
    public String viewMovieDetail(Model model, @PathVariable Long movieId){
        Optional<MovieEntity> optionalMovieFromEntity = movieRepo.findById(movieId);
        if (optionalMovieFromEntity.isPresent()){
            List<GenreEntity> genres = movieRepo.findById(movieId).get().getGenres();
            model.addAttribute("film", optionalMovieFromEntity.get());
            model.addAttribute("genres", genres);
            model.addAttribute("raiting", optionalMovieFromEntity.get().getRaitings());
            return "movie_detail";
        }

        return "redirect:/";

    }

    @GetMapping("/show_movie_details")
    public String showMovieDetails(Model model){
        RestTemplate restTemplate = new RestTemplate();
        MovieObject searchMovie = restTemplate.getForObject(SearchMovies.SEARCH_URL, MovieObject.class);
        List<Genre> genres = new ArrayList<>();
        searchMovie.getGenres().forEach(genres::add);
        model.addAttribute("film", searchMovie);
        model.addAttribute("genres", genres);
        return "movie_detail";
    }

}
