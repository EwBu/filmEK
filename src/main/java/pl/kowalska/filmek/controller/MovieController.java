package pl.kowalska.filmek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import pl.kowalska.filmek.services.GenreService;
import pl.kowalska.filmek.services.MovieService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/{movieId}")
    public String viewMovieDetail(Model model, @PathVariable Long movieId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();


        MovieEntity selectedMovie = movieService.findSingleMovieInDatabase(movieId);
        if (selectedMovie!=null){
            model.addAttribute("film", selectedMovie);
            return "movie_detail";
        }
        return "redirect:/main";
    }

    @GetMapping("/movie_details/{id}")
    public String showMovieDetails(Model model, @PathVariable Long id){
        MovieObject movieWithDetails = movieService.findSingleMovieInTmdb(id.toString());
        if (movieWithDetails!=null){
            model.addAttribute("film", movieWithDetails);
            return "movie_detail";
        }
        return "redirect:/main";
    }

}
