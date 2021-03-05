package pl.kowalska.filmek.controller;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.moviePojo.*;


import pl.kowalska.filmek.repository.MovieRepository;
import pl.kowalska.filmek.repository.UserRepository;

import pl.kowalska.filmek.services.MovieService;
import pl.kowalska.filmek.services.UserService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AppController {

    private final UserService userService;
    private final MovieRepository movieRepo;
    private final MovieService movieService;
    private UserDto userDto;

    @Autowired
    public AppController(UserService userService, MovieRepository movieRepo, MovieService movieService) {
        this.userService = userService;
        this.movieRepo = movieRepo;
        this.movieService = movieService;
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping ("/successLogin")
    public String successLogin(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.loadUserByEmail(email);
        userDto = new UserDto(user.getUserName(),user.getEmail(),user.getPassword(), user.getGender());
        return "redirect:/main";
    }



    @GetMapping("/main")
    public String viewHomePage(
            @RequestParam(value = "search", required = false) String q,
            Model model,
            Pageable pageable){

        Page<MovieEntity> moviesPage = movieService.findAll(pageable);
        PageWrapper<MovieEntity> pageNumbers = new PageWrapper<MovieEntity>(moviesPage, "/main");
        List<MovieEntity> listMovieEntities = movieRepo.findAll();

        model.addAttribute("listMovies", moviesPage);
        model.addAttribute("loggedUser", userDto);

            model.addAttribute("page", pageNumbers);

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
    public String filterMovies(String genre, Model model, Double voteMin, Double voteMax, Double popularityMin, Double popularityMax, Integer yearMin, Integer yearMax){
        voteMin = voteMin == null? 0.0: voteMin;
        voteMax = voteMax == null? 10.0: voteMax;
        popularityMin = popularityMin == null? 0.0: popularityMin;
        popularityMax = popularityMax == null? Double.MAX_VALUE: popularityMax;
        yearMin = yearMin == null? 1800: yearMin;
        yearMax = yearMax == null? 2099: yearMax;
        List<MovieEntity> moviesByQuery= movieService.findMoviesByQuery(genre, voteMin, voteMax, popularityMin, popularityMax, yearMin, yearMax);
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
        List<User> listUsers = userService.findAll();
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
