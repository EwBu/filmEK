package pl.kowalska.filmek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.ConfirmationToken;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.repository.ConfirmationTokenRepository;
import pl.kowalska.filmek.services.EmailSenderService;
import pl.kowalska.filmek.services.UserService;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Controller
public class UserRegistrationController {

    private UserService userService;
    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenRepository confirmationTokenRepository;


    public UserRegistrationController(UserService userService, EmailSenderService emailSenderService, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @ModelAttribute("user")
    public UserDto userRegistrationDto(){
        return new UserDto();
    }

    @GetMapping("/registration")
    public String showRegistrationForm(){
        return "register";
    }

    @Value("${spring.mail.username}")
    String mailFrom;

    @Resource
    private JavaMailSender javaMailSender;

    @PostMapping("/registration")
    public String registerUserAccount(@ModelAttribute("user") UserDto registrationDto) throws MessagingException {

        User userWithSameEmail = userService.findUserByEmail(registrationDto.getEmail());
        User userWithSameUsername = userService.findUserByUsername(registrationDto.getUserName());
        if(userWithSameEmail != null)
        {
            return "redirect:/registration?theSameEmail";
        }
        if(userWithSameUsername != null)
        {
            return "redirect:/registration?theSameUsername";
        }
        else {
            userService.save(registrationDto);
            User user = userService.findUserByUsername(registrationDto.getUserName());

            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepository.save(confirmationToken);

            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            mailMessage.setSubject("Dokończ rejestrację!", "UTF-8");

            MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "UTF-8");
            helper.setTo(user.getEmail());
            helper.setFrom("serwis.filmek@gmail.com");
            helper.setText("Aby potwierdzić swoje konto przejdź kliknij przycisk: "
                    +"<br><a href=\""+String.format("http://localhost:8075/confirm-account?token=%s\"",confirmationToken.getConfirmationToken())+"><button>"+
                    "AKTYWUJ KONTO"+"</button></a>",true);

            javaMailSender.send(mailMessage);


            return "redirect:/registration?verifyAccount";
        }
    }


    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null)
        {
            User user = userService.findUserByEmail(token.getUser().getEmail());
            user.setConfirmed(true);
            userService.update(user);
            modelAndView.setViewName("accountVerified");
        }
        else
        {
            modelAndView.addObject("message","Token nie istnieje lub jest uszkodzony");
            modelAndView.setViewName("error");
        }

        return modelAndView;
    }


}
