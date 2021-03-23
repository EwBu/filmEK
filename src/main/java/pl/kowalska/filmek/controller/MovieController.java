package pl.kowalska.filmek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kowalska.filmek.dto.Ocena;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.moviePojo.MovieObject;
import pl.kowalska.filmek.services.MovieService;

@Controller
//@RequestMapping(value = "/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;


    @GetMapping("/movie/{movieId}")
    public String viewMovieDetail(Model model, @PathVariable Long movieId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();


        MovieEntity selectedMovie = movieService.findSingleMovieInDatabase(movieId);
        if (selectedMovie!=null){
            model.addAttribute("film", selectedMovie);
            model.addAttribute("ocena",new Ocena());
            return "movie_detail";
        }
        return "redirect:/main";
    }


    @GetMapping("/movie/movie_details/{id}")
    public String showMovieDetails(Model model, @PathVariable String id){
        MovieObject movieWithDetails = movieService.findSingleMovieInTmdb(id);
        if (movieWithDetails!=null){
            model.addAttribute("film", movieWithDetails);
            return "movie_detail";
        }
        return "redirect:/main";
    }

    @PostMapping("/edit/{movieId}")
    public String AddRatingToMovie(Model model, @PathVariable Long movieId, Ocena ocena){
        return "redirect:/main";
    }

}
