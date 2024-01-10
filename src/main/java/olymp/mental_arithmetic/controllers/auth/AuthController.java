package olymp.mental_arithmetic.controllers.auth;

import olymp.mental_arithmetic.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserStorage userStorage;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserStorage userStorage) {
        this.authenticationManager = authenticationManager;
        this.userStorage = userStorage;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/auth/sign-up")
    public String register(Model model) {
        return "auth/register";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }


//    @PostMapping("/auth/register")
//    public String register(@ModelAttribute UserCreate userCreate) {
//        System.out.println(userCreate.getLastname());
//        System.out.println(userCreate.getBranchId());
//        try {
//            userService.createTempUser(userCreate);
//
//            return "redirect:/login";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "redirect:/error?message1=" + e.getMessage();
//        }
//    }
}
