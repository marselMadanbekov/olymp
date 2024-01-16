package olymp.mental_arithmetic.controllers.admin;


import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.OlympiadExercise;
import olymp.mental_arithmetic.model.entities.Tour;
import olymp.mental_arithmetic.services.exercise.OlympiadExerciseStorage;
import olymp.mental_arithmetic.services.olympiad.OlympiadService;
import olymp.mental_arithmetic.services.olympiad.OlympiadStorage;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/olympiads")
public class AdminOlympiadsController {

    private final OlympiadService olympiadService;
    private final OlympiadStorage olympiadStorage;
    private final OlympiadExerciseStorage olympiadExerciseStorage;
    @Autowired
    public AdminOlympiadsController(OlympiadService olympiadService, OlympiadStorage olympiadStorage, OlympiadExerciseStorage olympiadExerciseStorage) {
        this.olympiadService = olympiadService;
        this.olympiadStorage = olympiadStorage;
        this.olympiadExerciseStorage = olympiadExerciseStorage;
    }

    @GetMapping()
    public String mainPage(Model model){
        List<Olympiad> olympiads = olympiadStorage.findAllOlympiads();

        model.addAttribute("olympiads",olympiads);
        return "admin/main";
    }

    @GetMapping("/olympiad-details")
    public String olympiadDetails(@RequestParam Long olympiadId,
                                  Model model){
        Olympiad olympiad = olympiadStorage.getOlympiadById(olympiadId);
        List<OlympiadExercise> olympiadExercises = olympiadExerciseStorage.findByOlympiad(olympiad);
        List<Tour> tours = olympiadStorage.findAllToursByOlympiad(olympiad);

        model.addAttribute("tours", tours);
        model.addAttribute("exercises", olympiadExercises);
        model.addAttribute("olympiad", olympiad);
        return "admin/olympiad-details";
    }

    @GetMapping("/create-olympiad")
    public String createOlympiad(){
        return "admin/create-olympiad";
    }


    @PostMapping("/create-olympiad")
    public ResponseEntity<Map<String,String>> createOlympiad(@RequestParam String name,
                                                             @RequestParam Double cost,
                                                             @RequestParam String startDate){
        Map<String,String> response = new HashMap<>();
        try{
            olympiadService.createOlympiad(name,cost,startDate);

            response.put("message", "Олимпиада успешно создана!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }
}
