package olymp.mental_arithmetic.controllers.admin;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.services.exercise.OlympiadExerciseService;
import olymp.mental_arithmetic.services.olympiad.OlympiadStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/exercises")
public class AdminExercisesController {

    private final OlympiadStorage olympiadStorage;
    private final OlympiadExerciseService olympiadExerciseService;

    @Autowired
    public AdminExercisesController(OlympiadStorage olympiadStorage, OlympiadExerciseService olympiadExerciseService) {
        this.olympiadStorage = olympiadStorage;
        this.olympiadExerciseService = olympiadExerciseService;
    }

    @GetMapping("/create-exercise")
    public String createExerciseToOlympiad(@RequestParam Long olympiadId,
                                           Model model){
        Olympiad olympiad = olympiadStorage.getOlympiadById(olympiadId);
        List<Level> levels = olympiadStorage.findAllLevels();

        model.addAttribute("olympiad", olympiad);
        model.addAttribute("levels", levels);
        return "admin/create-exercise";
    }

    @PostMapping("/create-exercise")
    public ResponseEntity<Map<String,String>> createExercise(@RequestParam Long olympiadId,
                                                             @RequestParam Long levelId,
                                                             @RequestParam MultipartFile excel){
        Map<String,String> response = new HashMap<>();
        try{
            olympiadExerciseService.createExercise(olympiadId,levelId,excel);
            response.put("message", "Задания успешно созданы!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
