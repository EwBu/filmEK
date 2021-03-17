package pl.kowalska.filmek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.services.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/profile")
@SessionAttributes("user")
public class ProfileController {


    private UserService userService;

    public ProfileController(UserService userService){
        this.userService = userService;
    }

    private UserDto userDto;

    @RequestMapping("")
    public String showUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User userByEmail = userService.findUserByEmail(username);
        userDto = new UserDto(userByEmail.getUserId(), userByEmail.getUserName(), userByEmail.getEmail(),"dirtyDancing",userByEmail.getGender(),userByEmail.isConfirmed());

        model.addAttribute("user", userDto);
        return "profile";
    }


    @PostMapping(value = "/editUser")
    public String editUser(@ModelAttribute("user") UserDto userDto, String confirmpass, BindingResult bindingResult, Model model) {

        if(!userDto.getPassword().equals(confirmpass)){
            model.addAttribute("err", "Podane hasła różnią się");
        }else if(userDto.getEmail() == null || userDto.getEmail() == ""){
            model.addAttribute("err", "Niepoprawny adres email");
        }else if(userDto.getUserName() == null || userDto.getUserName() == "") {
            model.addAttribute("err", "Niepoprawna nazwa użytkownika");
        }else{
            userService.updateUser(userDto);

            model.addAttribute("success", "Zmiana danych przebiegła pomyślnie");
            System.out.println("przeszło");

            return "redirect:/main";

        }
        return "profile";
    }
}