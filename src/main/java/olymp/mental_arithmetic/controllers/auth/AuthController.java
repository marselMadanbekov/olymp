package olymp.mental_arithmetic.controllers.auth;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.utils.UserCreate;
import olymp.mental_arithmetic.services.olympiad.OlympiadStorage;
import olymp.mental_arithmetic.services.user.UserService;
import olymp.mental_arithmetic.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserStorage userStorage;
    private final OlympiadStorage olympiadStorage;
    private final UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserStorage userStorage, OlympiadStorage olympiadStorage, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userStorage = userStorage;
        this.olympiadStorage = olympiadStorage;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/sign-up")
    public String register(Model model) {
        List<Level> levels = olympiadStorage.findAllLevels();
        model.addAttribute("levels", levels);
        return "auth/register";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }


    @PostMapping("/auth/register")
    public String register(@ModelAttribute UserCreate userCreate) {
        try {
            userService.createTempUser(userCreate);
            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error?message1=" + e.getMessage();
        }
    }
}
