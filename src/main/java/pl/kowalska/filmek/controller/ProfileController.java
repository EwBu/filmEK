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
import pl.kowalska.filmek.services.UserService;


@Controller
@RequestMapping("/profile")
public class ProfileController {


    private UserService userService;
    private UserDto userDto;

    public ProfileController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping("")
    public String showUser(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("user", userService.findUserByEmail(username));
        return "profile";
    }


//    @PostMapping(value = "/editUser")
//    public String editUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) throws /*InputException*/ DatabaseException {
//         if (userDao.checkMail(user.getEmail()) != null) {
//            model.addAttribute("badEmail", "Podany adres email jest już w użyciu");
//            return "profile";
//        } else if (user.getHash().equals(user.getConfirmHash()) == false) {
//            model.addAttribute("wrongPassword", "Podane hasła różnią się");
//            return "profile";
//        }
//
//        model.addAttribute("success", "Rejestracja przebiegła pomyślnie , mozesz sie zalogowac");
//        userService.createUser(user);
//
//        return "redirect:/profile";
//
//    }
}