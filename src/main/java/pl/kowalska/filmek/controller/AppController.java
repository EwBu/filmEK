package pl.kowalska.filmek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pl.kowalska.filmek.SearchMovies;
import pl.kowalska.filmek.model.GenreEntity;
import pl.kowalska.filmek.model.MovieEntity;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.moviePojo.*;

import pl.kowalska.filmek.repository.GenreRepository;
import pl.kowalska.filmek.repository.MovieRepository;
import pl.kowalska.filmek.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    private GenreRepository genreRepository;


//    @Autowired
//    private EmailSenderService emailSenderService;

    @GetMapping("")
    public String viewHomePage(Model model){
        List<MovieEntity> listMovieEntities = movieRepo.findAll();
        model.addAttribute("listMovies", listMovieEntities);
        String searchText="";
        model.addAttribute("searchText", searchText);
        return "index";
    }

    @GetMapping("/show/{movieName}")
    public String viewHomePage(String movieName, Model model){
//        List<MovieEntity> listMovieEntities = movieRepo.findAll();
        System.out.println(movieName);


//        model.addAttribute("listMovies", listMovieEntities);
//        String searchText="";
//        model.addAttribute("searchText", searchText);
        return "index";
    }

    @GetMapping("/register")
    public String viewRegisterForm(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/process_register")
    public String processRegister(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPass = encoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        userRepo.save(user);
//        ConfirmationToken confirmationToken = new ConfirmationToken(user);
//        tokenRepo.save(confirmationToken);
//
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(user.getEmail());
//        mailMessage.setSubject("Ukończ rejestrację");
//        mailMessage.setFrom("YOUR EMAIL ADDRESS");
//        mailMessage.setText("Aby potwierdzić tworzenie konta kliknij w link aktywacyjny: : "
//                +"http://localhost:8080/confirm-account?token="+confirmationToken.getConfirmationToken());
//        emailSenderService.sendEmail(mailMessage);
        return "register_success";
    }

//    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
//    public String confirmUserAccount(Model model, @RequestParam("token")String confirmationToken)
//    {
//
//
//        if(token != null)
//        {
//            User user = userRepo.findByEmail(token.getUser().getEmail());
//            user.setEnabled(true);
//            userRepo.save(user);
//            return "accountVerified";
//        }
//        else
//        {
//            model.addAttribute("message","The link is invalid or broken!");
//            return "error";
//        }
//
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
    @GetMapping("/showMovie/{movieId}")
    public String viewMovieDetail(Model model, @PathVariable Long movieId){
        Optional<MovieEntity> optionalMovieFromEntity = movieRepo.findById(movieId);
        if (optionalMovieFromEntity.isPresent()){
            List<GenreEntity> genres = movieRepo.findById(movieId).get().getGenres();
            model.addAttribute("film", optionalMovieFromEntity.get());
            model.addAttribute("genres", genres);
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

    @GetMapping("/loadGenres")
        public void loadGenresToDb(){
            RestTemplate restTemplate = new RestTemplate();
            ListOfGenres genresList= restTemplate.getForObject("https://api.themoviedb.org/3/genre/movie/list?api_key=e529d754811a8187c547ac59aa92495d&language=pl", ListOfGenres.class);
            List<Genre> genres = genresList.getGenres();

            genres.forEach(genre -> {
                GenreEntity genreEntity = new GenreEntity(Long.valueOf(genre.getId()),genre.getName());
                genreRepository.save(genreEntity);
            } );
            System.out.println("DONE");
        }

        @GetMapping("/Movies")
        public void loadMoviesToDb(){
            RestTemplate restTemplate = new RestTemplate();
            MoviesList moviesList= restTemplate.getForObject("https://api.themoviedb.org/3/movie/popular?api_key=e529d754811a8187c547ac59aa92495d&language=pl&page=3", MoviesList.class);
            List<Result> results = moviesList.getResults();

//                                                        (Integer id, String posterPath, String polishTitle, String originalTitle, String originalLanguage, String overview, double popularity, String releaseDate, long runtime, double voteAverage, long voteCount, Set<GenreEntity> genres)
            results.forEach(movie -> {
                List<GenreEntity> genresForCurrentMovie = new ArrayList<>();
                movie.getGenreIds().forEach(genre ->genresForCurrentMovie.add(genreRepository.getOne(Long.valueOf(genre))));
                MovieEntity movieEntity = new MovieEntity(movie.getId(), movie.getPosterPath(), movie.getTitle(), movie.getOriginalTitle(), movie.getOriginalLanguage(), movie.getOverview(), movie.getPopularity(), movie.getReleaseDate(), movie.getVoteAverage(), movie.getVoteCount(), genresForCurrentMovie);// Trzeba przerobić tabele movies zeby przyjmowała to co potrzebujemy, trzeba tez przerobic encje
                if(movieEntity.getOverview() != "") {
                    movieRepo.save(movieEntity);
                }

            } );
            System.out.println("DONE");
        }



}
