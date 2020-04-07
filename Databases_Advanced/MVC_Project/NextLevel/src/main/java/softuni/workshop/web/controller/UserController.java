package softuni.workshop.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.workshop.service.service.UserService;
import softuni.workshop.web.model.UserRegisterModel;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView register() {

        return new ModelAndView("user/register", "title", "Register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterModel userRegisterModel) {

        if (!userRegisterModel.getPassword().equals(userRegisterModel.getConfirmPassword())) {
            return new ModelAndView("redirect:/users/register");
        }
        userService.registerUser(userRegisterModel);
        return super.redirect("/users/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return super.view("user/login");
    }


}
