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
import pl.kowalska.filmek.dto.UserDto;
import pl.kowalska.filmek.model.User;
import pl.kowalska.filmek.services.UserService;

@Controller
@RequestMapping("/profile")
public class ProfileController {


    private UserService userService;

    public ProfileController(UserService userService){
        this.userService = userService;
    }

//    @ModelAttribute("user")
//    public UserDto userDto(){
//        return new UserDto();
//    }

    UserDto userDto;
    @RequestMapping("")
    public String showUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User userByEmail = userService.findUserByEmail(username);
        userDto = new UserDto(userByEmail.getUserName(), userByEmail.getEmail(),"qwertyuiop",'M',true );

        model.addAttribute("user", userDto);
        return "profile";
    }


    @PostMapping(value = "/editUser")
    public String editUser(UserDto userDto, String confirmpass, BindingResult bindingResult, String emailChange, Model model) {
         if (userService.findUserByEmail(emailChange) != null) {
            model.addAttribute("badEmail", "Podany adres email jest już w użyciu");
            return "profile";
        }
//         else if (userDto.getPassword().equals(confirmpass) == false) {
//            model.addAttribute("wrongPassword", "Podane hasła różnią się");
//            return "profile";
//        }


        User user = userService.findUserByEmail(userDto.getEmail());

        model.addAttribute("success", "Rejestracja przebiegła pomyślnie , mozesz sie zalogowac");
        System.out.println("przeszło");
        userService.updateUser(user);

        return "redirect:/main";

    }
}