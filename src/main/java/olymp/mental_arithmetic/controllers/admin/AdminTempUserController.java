package olymp.mental_arithmetic.controllers.admin;

import olymp.mental_arithmetic.model.entities.TempUser;
import olymp.mental_arithmetic.services.user.UserService;
import olymp.mental_arithmetic.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin/temp-users")
public class AdminTempUserController {

    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public AdminTempUserController(UserStorage userStorage, UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public String tempUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
                            Model model) {
        Page<TempUser> tempUsers = userStorage.findTempUsersByPage(page);
        model.addAttribute("tempUsers", tempUsers);
        return "admin/temp-users";
    }

    @GetMapping("/temp-user-details")
    public String tempUserDetails(@RequestParam Long userId,
                                  Model model){
        TempUser tempUser = userStorage.getTempUserById(userId);

        model.addAttribute("tempUser", tempUser);
        return "admin/temp-user-details";
    }

    @PostMapping("/apply")
    public ResponseEntity<Map<String,String>> applyTempUser(@RequestParam Long tempUserId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.applyTempUser(tempUserId);
            response.put("message", "Пользователь зачислен в участники!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/reject")
    public ResponseEntity<Map<String,String>> rejectTempUser(@RequestParam Long tempUserId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.rejectTempUser(tempUserId);
            response.put("message", "Пользователь удален!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
