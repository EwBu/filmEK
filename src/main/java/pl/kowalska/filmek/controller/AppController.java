package pl.kowalska.filmek.controller;


import com.sun.xml.bind.v2.util.QNameMap;
import org.dom4j.rule.Mode;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.moviePojo.*;


import pl.kowalska.filmek.repository.GenreRepository;
import pl.kowalska.filmek.repository.MovieRepository;
import pl.kowalska.filmek.repository.UserRepository;

import pl.kowalska.filmek.services.MovieService;

import java.util.*;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private MovieService movieService;


    @GetMapping("/login")
    public String login(){
        return "login";
    }


    @GetMapping("/main")
    public String viewHomePage(@RequestParam(value = "search", required = false) String q,  Model model){
        List<MovieEntity> listMovieEntities = movieRepo.findAll();
        model.addAttribute("listMovies", listMovieEntities);

        if (q!=null){
            return String.format("redirect:/?search=%s",q);
        }
        return "index";
    }

    @GetMapping("/")
    public String search(@RequestParam(value = "search", required = false) String q, Model model) {

        RestTemplate restTemplate = new RestTemplate();
        MoviesList moviesList = restTemplate.getForObject("https://api.themoviedb.org/3/search/movie?api_key=e529d754811a8187c547ac59aa92495d&language=pl&query=" + q, MoviesList.class);
        if (q!=null) {
            List<Result> searchResults = moviesList.getResults();
            model.addAttribute("search", searchResults);
            return "index";
        }
       return "redirect:/main";
    }

    @RequestMapping(value="/filter_movies", method = {RequestMethod.POST, RequestMethod.GET} )
    public String filterMovies(String genre, Model model, Double voteMin, Double voteMax, Double popularityMin, Double popularityMax){
        voteMin = voteMin == null? 0.0: voteMin;
        voteMax = voteMax == null? 10.0: voteMax;
        popularityMin = popularityMin == null? 0.0: popularityMin;
        popularityMax = popularityMax == null?Double.MAX_VALUE: popularityMax;
        List<MovieEntity> moviesByQuery= movieService.findMoviesByQuery(genre, voteMin, voteMax, popularityMin, popularityMax);
        model.addAttribute("listMovies", moviesByQuery);

        return "filtered_movies";
    }

//    @GetMapping("/register")
//    public String viewRegisterForm(Model model){
//        model.addAttribute("user", new User());
//        return "register";
//    }

//    @PostMapping("/process_register")
//    public String processRegister(User user){
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        String encodedPass = encoder.encode(user.getPassword());
//        user.setPassword(encodedPass);
//        userRepo.save(user);
//        return "register_success";
//    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model){
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }


//    @GetMapping("/")
//    public String viewSerchedMovies(Model model){
//        String movieTitle = null;
//        model.addAttribute("title", movieTitle);
//        return "index";
//    }


}
