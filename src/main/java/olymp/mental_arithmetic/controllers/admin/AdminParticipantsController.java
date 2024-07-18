package olymp.mental_arithmetic.controllers.admin;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Participant;
import olymp.mental_arithmetic.model.entities.Tour;
import olymp.mental_arithmetic.model.utils.UserCreate;
import olymp.mental_arithmetic.model.utils.UserdataUpdate;
import olymp.mental_arithmetic.services.olympiad.OlympiadStorage;
import olymp.mental_arithmetic.services.user.UserService;
import olymp.mental_arithmetic.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/participants")
public class AdminParticipantsController {
    private final UserService userService;
    private final UserStorage userStorage;
    private final OlympiadStorage olympiadStorage;

    @Autowired
    public AdminParticipantsController(UserService userService, UserStorage userStorage, OlympiadStorage olympiadStorage) {
        this.userService = userService;
        this.userStorage = userStorage;
        this.olympiadStorage = olympiadStorage;
    }

    @GetMapping
    public String participants(@RequestParam(required = false,defaultValue = "0") Integer page,
                               Model model){
        Page<Participant> participants = userStorage.findAllParticipants(page);

        model.addAttribute("participants", participants);
        return "admin/participants";
    }

    @GetMapping("/participant-details")
    public String participantDetails(@RequestParam Long participantId,
                                     Model model){
        Participant participant = userStorage.getParticipantById(participantId);
        List<Tour> tours = olympiadStorage.findAllTours();
        List<Level> levels = olympiadStorage.findAllLevels();
        model.addAttribute("participant", participant);
        model.addAttribute("levels", levels);
        model.addAttribute("tours", tours);
        return "admin/participant-details";

    }

    @PostMapping("/apply-tour")
    public ResponseEntity<Map<String,String>> applyTour(@RequestParam Long participantId,
                                                        @RequestParam Long tourId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.applyParticipantTour(participantId, tourId);
            response.put("message","Участник успешно зачислен!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/update-user-data")
    public ResponseEntity<Map<String,String>> updateUserData(@ModelAttribute UserdataUpdate update){
        Map<String,String> response = new HashMap<>();
        try{
            userService.updateUserdata(update);
            response.put("message", "Данные успешно изменены");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/set-level")
    public ResponseEntity<Map<String,String>> setLevel(@RequestParam Long participantId,
                                                       @RequestParam Long levelId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.setLevelParticipant(participantId,levelId);
            response.put("message", "Уровень успешно назначен!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/delete-participant")
    public ResponseEntity<Map<String,String>> deleteParticipant(@RequestParam Long participantId){
        Map<String,String> response = new HashMap<>();
        try{
            userService.deleteParticipantById(participantId);
            response.put("message", "Участник успешно удален!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/create-participant")
    public String createParticipant(Model model){
        List<Level> levels = olympiadStorage.findAllLevels();

        model.addAttribute("levels", levels);
        return "admin/create-participant";
    }

    @PostMapping("/create-participant")
    public ResponseEntity<Map<String,String>> createPupilReq(@ModelAttribute UserCreate userCreate){
        Map<String,String> response = new HashMap<>();
        try{
            userService.createParticipant(userCreate);
            response.put("message", "Ученик успешно создан");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
