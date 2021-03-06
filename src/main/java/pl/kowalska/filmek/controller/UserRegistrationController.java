package pl.kowalska.filmek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.ConfirmationToken;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.repository.ConfirmationTokenRepository;
import pl.kowalska.filmek.services.UserService;

import javax.mail.MessagingException;


@Controller
public class UserRegistrationController {

    private UserService userService;
    private final ConfirmationTokenRepository confirmationTokenRepository;


    public UserRegistrationController(UserService userService, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userService = userService;
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
            userService.handleConfirmationMailSending(registrationDto);
            return "redirect:/registration?verifyAccount";
        }
    }


    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
    {

        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if(token != null)
        {
            userService.updateUser(token);
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
