package pl.kowalska.filmek.controller;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
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
    public String login() {
        return "login";
    }

    @PostMapping("/successLogin")
    public String successLogin() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByEmail(email);
        if (!user.isConfirmed()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            return "accountNotVerified";
        }
        userDto = new UserDto(user.getUserId(), user.getUserName(), user.getEmail(), user.getPassword(), user.getGender(), user.isConfirmed());
        return "redirect:/main";
    }

    @GetMapping("/main")
    public String viewHomePage(
            Model model,
            Pageable pageable) {

        Page<MovieEntity> moviesPage = movieService.findAll(pageable);
        PageWrapper<MovieEntity> pageNumbers = new PageWrapper<MovieEntity>(moviesPage, "/main");
//        List<MovieEntity> listMovieEntities = movieRepo.findAll();

        model.addAttribute("listMovies", moviesPage);
        model.addAttribute("loggedUser", userDto);

        model.addAttribute("page", pageNumbers);
//
//        if (q != null) {
//            return String.format("redirect:/?search=%s", q);
        return "index";

    }

    @GetMapping("/")
    public String init() {
        return "redirect:/main";
    }


    @GetMapping("/list_users")
    public String viewUsersList(Model model) {
        List<User> listUsers = userService.findAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    private String deleteUser(@RequestParam String userId) {
        userService.deleteUser(Long.valueOf(userId));
        return "redirect:/list_users";
    }
}

