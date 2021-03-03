package pl.kowalska.filmek.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.services.UserService;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    private UserService userService;

    public UserRegistrationController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserDto userRegistrationDto(){
        return new UserDto();
    }

    @GetMapping
    public String showRegistrationForm(){
        return "register";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserDto registrationDto){
        userService.save(registrationDto);
        return "redirect:/registration?success";
    }
}
