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
            User user = userService.retrieveUserFromSecurityContext();
            MovieEntity singleMovieInDatabase = movieService.findSingleMovieInDatabase(movieId);
            MovieRaiting movieRaiting = movieRaitingService.findByMovieId(new MovieRaitingKey(user.getUserId(), singleMovieInDatabase.getId()));
            model.addAttribute("currentRaiting", movieRaiting);
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

    @PostMapping("/edit/{movieId}")
    public String AddRatingToMovie(@PathVariable Long movieId, Raiting raiting){
        //        User userFromDb = userRepository.findByEmail("admin@wp.pl");
//
        MovieEntity singleMovieInDatabase = movieService.findSingleMovieInDatabase(movieId);
        User user = userService.retrieveUserFromSecurityContext();
        MovieRaiting movieRaiting = new MovieRaiting(new MovieRaitingKey(user.getUserId(),singleMovieInDatabase.getId()), raiting.getOcena(), true);
        movieRaitingService.save(movieRaiting);
        return "redirect:/main";
    }

 @GetMapping("/edit/{movieId}/{ocena}")
    public String AddRatingToMovie2(@PathVariable Long movieId, @PathVariable Integer ocena){
        //        User userFromDb = userRepository.findByEmail("admin@wp.pl");
//
        MovieEntity singleMovieInDatabase = movieService.findSingleMovieInDatabase(movieId);
        User user = userService.retrieveUserFromSecurityContext();
//        MovieRaiting movieRaiting = new MovieRaiting(new MovieRaitingKey(user.getUserId(),singleMovieInDatabase.getId()), raiting.getOcena(), true);
//        movieRaitingService.save(movieRaiting);
        return "redirect:/main";
    }



}
