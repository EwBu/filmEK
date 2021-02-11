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
import pl.kowalska.filmek.moviePojo.Genre;

import pl.kowalska.filmek.moviePojo.ListOfGenres;
import pl.kowalska.filmek.moviePojo.MoviesList;
import pl.kowalska.filmek.moviePojo.Result;
import pl.kowalska.filmek.repository.GenreRepository;
import pl.kowalska.filmek.repository.MovieRepository;
import pl.kowalska.filmek.repository.UserRepository;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    @Autowired
    GenreRepository genreRepository;


//    @Autowired
//    private EmailSenderService emailSenderService;

    @GetMapping("")
    public String viewHomePage(Model model){
        List<MovieEntity> listMovieEntities = movieRepo.findAll();
        model.addAttribute("listMovies", listMovieEntities);
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
        MoviesList moviesList= restTemplate.getForObject("https://api.themoviedb.org/3/movie/top_rated?api_key=e529d754811a8187c547ac59aa92495d&language=pl", MoviesList.class);
        List<Result> results = moviesList.getResults();


//                                                        (Integer id, String posterPath, String polishTitle, String originalTitle, String originalLanguage, String overview, double popularity, String releaseDate, long runtime, double voteAverage, long voteCount, Set<GenreEntity> genres)
        results.forEach(movie -> {
            MovieEntity movieEntity = new MovieEntity(movie.getId(), movie.getPosterPath(), movie.getTitle(), movie.getOriginalTitle(), movie.getOriginalLanguage(), movie.getOverview(), movie.getPopularity(), movie.getReleaseDate(), movie.getVoteAverage(), movie.getVoteCount());// Trzeba przerobić tabele movies zeby przyjmowała to co potrzebujemy, trzeba tez przerobic encje
            if(movieEntity.getOverview() != "") {
                movieRepo.save(movieEntity);
            }

        } );
        System.out.println("DONE");
    }
}
