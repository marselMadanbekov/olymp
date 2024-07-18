package olymp.mental_arithmetic.controllers.admin;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.Participant;
import olymp.mental_arithmetic.model.entities.Tour;
import olymp.mental_arithmetic.services.olympiad.OlympiadService;
import olymp.mental_arithmetic.services.olympiad.OlympiadStorage;
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
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/tours")
public class AdminTourController {

    private final OlympiadStorage olympiadStorage;
    private final OlympiadService olympiadService;
    private final UserStorage userStorage;
    @Autowired
    public AdminTourController(OlympiadStorage olympiadStorage, OlympiadService olympiadService, UserStorage userStorage) {
        this.olympiadStorage = olympiadStorage;
        this.olympiadService = olympiadService;
        this.userStorage = userStorage;
    }

    @GetMapping("/add-participants-by-level")
    public String addByLevel(@RequestParam Long tourId,
                             Model model){
        Tour tour = olympiadStorage.getTourById(tourId);
        List<Level> levels = olympiadStorage.findAllLevels();

        model.addAttribute("tour", tour);
        model.addAttribute("levels", levels);
        return "admin/add-participants-by-level";
    }
    @GetMapping("/create-tour")
    public String tours(@RequestParam Long olympiadId,
                        Model model){
        Olympiad olympiad = olympiadStorage.getOlympiadById(olympiadId);
        model.addAttribute("olympiad", olympiad);
        return "admin/create-tour";
    }
    @GetMapping("/tour-details")
    public String tourDetails(@RequestParam Long tourId,
                              @RequestParam(required = false,defaultValue = "0") Integer page,
                              Model model){
        Tour tour = olympiadStorage.getTourById(tourId);
        Page<Participant> participants = userStorage.findUsersPageByTour(tour,page);
        model.addAttribute("participants", participants);
        model.addAttribute("tour", tour);
        return "admin/tour-details";
    }

    @PostMapping("/add-by-level")
    public ResponseEntity<Map<String,String>> addByLevel(@RequestParam Long tourId,
                                                         @RequestParam Long levelId){
        Map<String,String> response = new HashMap<>();
        try{
            olympiadService.addParticipantsToTourByLevel(tourId,levelId);
            response.put("message", "Все пользователи успешно добавлены!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/delete-tour")
    public ResponseEntity<Map<String,String>> deleteTour(@RequestParam Long tourId){
        Map<String,String> response = new HashMap<>();
        try {
            olympiadStorage.deleteTourById(tourId);
            response.put("message", "");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/remove-participant")
    public ResponseEntity<Map<String,String>> removeParticipant(@RequestParam Long participantId){
        Map<String,String> response = new HashMap<>();
        try{
            olympiadService.removeParticipantFromTour(participantId);
            response.put("message","Участник успешно отчислен!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/create-tour")
    public ResponseEntity<Map<String,String>> createTour(@RequestParam Long olympiadId,
                                                         @RequestParam String name,
                                                         @RequestParam String startDateTime,
                                                         @RequestParam String endDateTime){
        Map<String,String> response = new HashMap<>();
        try{
            olympiadService.createTour(olympiadId,name,startDateTime,endDateTime);
            response.put("message", "Тур успешно создан!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
