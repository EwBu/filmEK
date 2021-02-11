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

import java.util.ArrayList;
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



}
