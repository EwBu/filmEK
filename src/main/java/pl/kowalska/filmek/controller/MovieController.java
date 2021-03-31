package pl.kowalska.filmek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.kowalska.filmek.dto.Raiting;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.model.MovieRaiting;
import pl.kowalska.filmek.model.MovieRaitingKey;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.moviePojo.MovieObject;
import pl.kowalska.filmek.repository.UserRepository;
import pl.kowalska.filmek.services.MovieRaitingService;
import pl.kowalska.filmek.services.MovieService;
import pl.kowalska.filmek.services.UserService;

import java.util.Optional;

@Controller
//@RequestMapping(value = "/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieRaitingService movieRaitingService;

    @GetMapping("/movie/{movieId}")
    public String viewMovieDetail(Model model, @PathVariable Long movieId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        MovieEntity selectedMovie = movieService.findSingleMovieInDatabase(movieId);
        if (selectedMovie!=null){
            Optional<MovieRaiting> movieRatedByLoginUser = movieRaitingService.findRating(movieId);
            movieRatedByLoginUser.ifPresent(rating -> model.addAttribute("currentRaiting", rating));
            model.addAttribute("film", selectedMovie);
            model.addAttribute("ocena",new Raiting());
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


    @GetMapping("/edit/{movieId}")
    public String AddRatingToMovie(@PathVariable Long movieId, @RequestParam Integer ocena){
        //        User userFromDb = userRepository.findByEmail("admin@wp.pl");
//
        MovieEntity singleMovieInDatabase = movieService.findSingleMovieInDatabase(movieId);
        Optional<User> user = userService.retrieveUserFromSecurityContext();
        user.ifPresent(usr -> {
            MovieRaiting movieRaiting = new MovieRaiting(new MovieRaitingKey(usr.getUserId(),singleMovieInDatabase.getId()), ocena, true);
            movieRaitingService.save(movieRaiting);
        });
        return "redirect:/main";
    }

}
