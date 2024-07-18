package olymp.mental_arithmetic.controllers.auth;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.utils.UserCreate;
import olymp.mental_arithmetic.services.olympiad.OlympiadStorage;
import olymp.mental_arithmetic.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AuthController {

    private final OlympiadStorage olympiadStorage;
    private final UserService userService;

    @Autowired
    public AuthController(OlympiadStorage olympiadStorage, UserService userService) {
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
    public ResponseEntity<Map<String, String>> register(@ModelAttribute UserCreate userCreate) {
        Map<String, String> response = new HashMap<>();
        try {
            userService.createTempUser(userCreate);
            response.put("message", "Запрос на регистрацию успешно отправлен!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
