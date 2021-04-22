package pl.kowalska.filmek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.dto.RatedMoviesByUser;
import pl.kowalska.filmek.dto.RatingDto;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.model.MovieRaiting;
import pl.kowalska.filmek.model.MovieRaitingKey;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.moviePojo.MovieObject;
import pl.kowalska.filmek.moviePojo.MoviesList;
import pl.kowalska.filmek.moviePojo.Result;
import pl.kowalska.filmek.services.MovieRaitingService;
import pl.kowalska.filmek.services.MovieService;
import pl.kowalska.filmek.services.RatedMoviesByUserService;
import pl.kowalska.filmek.services.UserService;


import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieRaitingService movieRaitingService;

    @Autowired
    private RatedMoviesByUserService ratedMoviesByUserService;

    @RequestMapping(value ="/search_movies", method = {RequestMethod.POST, RequestMethod.GET})
    public String search(String search, Model model) {

        RestTemplate restTemplate = new RestTemplate();
        MoviesList moviesList = restTemplate.getForObject("https://api.themoviedb.org/3/search/movie?api_key=e529d754811a8187c547ac59aa92495d&language=pl&query=" + search, MoviesList.class);
        if (search != null) {
            List<Result> searchResults = moviesList.getResults();
            model.addAttribute("search", searchResults);
            return "search_movies";
        }
        return "index";
    }

    @RequestMapping(value = "/filter_movies", method = {RequestMethod.POST, RequestMethod.GET})
    public String filterMovies(String genre, Model model, Double voteMin, Double voteMax, Double popularityMin, Double popularityMax, Integer yearMin, Integer yearMax) {
        voteMin = voteMin == null ? 0.0 : voteMin;
        voteMax = voteMax == null ? 10.0 : voteMax;
        popularityMin = popularityMin == null ? 0.0 : popularityMin;
        popularityMax = popularityMax == null ? Double.MAX_VALUE : popularityMax;
        yearMin = yearMin == null ? 1800 : yearMin;
        yearMax = yearMax == null ? 2099 : yearMax;
        List<MovieEntity> moviesByQuery = movieService.findMoviesByQuery(genre, voteMin, voteMax, popularityMin, popularityMax, yearMin, yearMax);
        model.addAttribute("listMovies", moviesByQuery);

        return "filtered_movies";
    }

    @GetMapping("/movie/{movieId}")
    public String viewMovieDetail(Model model, @PathVariable Long movieId) {

        RatingDto ratingDto = new RatingDto();

        try {
            MovieEntity selectedMovie = movieService.findSingleMovieInDatabase(movieId);
            model.addAttribute("film", selectedMovie);

        } catch (NoSuchElementException n) {
            MovieObject selectedMovie = movieService.findSingleMovieInTmdb(String.valueOf(movieId));
            model.addAttribute("film", selectedMovie);
        }
        try {
            Optional<MovieRaiting> movieRatedByLoginUser = movieRaitingService.findRating(movieId);
            if (!movieRatedByLoginUser.isEmpty()) {
                movieRatedByLoginUser.ifPresent(rating -> {
                    RatingDto ratingDto1 = new RatingDto(rating.getRaitingId().getId(), rating.getRaitingId().getUserId(), rating.getRating(), rating.isToWatch());
                    model.addAttribute("ratingDto", ratingDto1);
                });
            } else {
                ratingDto.setId(movieId);
                model.addAttribute("ratingDto", ratingDto);
            }
        }catch (NoSuchElementException n) {
            ratingDto.setId(movieId);
            model.addAttribute("ratingDto", ratingDto);
        }
        return "movie_detail";
    }

    @PostMapping("/movie/edit")
    public String addRatingToMovie(@ModelAttribute RatingDto rating,
                                   @RequestParam(value="action", required=false) String action) {

        Long movieId = rating.getId();
        if(!movieService.checkMovieInDb(movieId)){
            movieService.saveMovieToDb(movieId);
        }
        MovieEntity singleMovieInDatabase = movieService.findSingleMovieInDatabase(movieId);

        Optional<User> user = userService.retrieveUserFromSecurityContext();
        user.ifPresent(usr -> {
            if(action!=null){if (action.equals("changeRating"))rating.setRating(0);}  // && rating.getToWatch().equals(0)){movieRaitingService.
            MovieRaiting movieRaiting = new MovieRaiting(new MovieRaitingKey(usr.getUserId(), singleMovieInDatabase.getId()), rating.getRating(), rating.getToWatch());
            movieRaitingService.save(movieRaiting);
        });
        return String.format("redirect:/movie/%d",movieId);
    }

    @GetMapping("/rated_movies/my")
    public String showRatedMovies(Model model) {

        Optional<User> user = userService.retrieveUserFromSecurityContext();

        List<RatedMoviesByUser> moviesRatedByUser = ratedMoviesByUserService.findRatedMovies(user.get().getUserId());
        model.addAttribute("moviesRatedByUser", moviesRatedByUser);

        return "rated_movies";
    }

    @RequestMapping(value = "/rated_movies/{userName}", method = {RequestMethod.GET, RequestMethod.POST})
    public String showRatedMoviesByUser(@PathVariable String userName, Model model){

        User userByUsername = userService.findUserByUsername(userName);
        List<RatedMoviesByUser> moviesRatedByUser = ratedMoviesByUserService.findRatedMovies(userByUsername.getUserId());
        model.addAttribute("moviesRatedByUser", moviesRatedByUser);
        return "rated_movies";
    }

    @GetMapping("/toWatch_movies")
    public String showToWatchMovies(Model model) {

        Optional<User> user = userService.retrieveUserFromSecurityContext();

        List<RatedMoviesByUser> moviesToWatch = ratedMoviesByUserService.findtoWatchMovies(user.get().getUserId());
        model.addAttribute("moviesToWatch", moviesToWatch);

        return "toWatch_movies";
    }

}
