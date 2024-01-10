package olymp.mental_arithmetic.controllers.admin;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.OlympiadExercise;
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
@RequestMapping("/admin/levels")
public class AdminLevelController {

    private final OlympiadService olympiadService;
    private final OlympiadStorage olympiadStorage;
    private final OlympiadExerciseStorage olympiadExerciseStorage;
    @Autowired
    public AdminLevelController(OlympiadService olympiadService, OlympiadStorage olympiadStorage, OlympiadExerciseStorage olympiadExerciseStorage) {
        this.olympiadService = olympiadService;
        this.olympiadStorage = olympiadStorage;
        this.olympiadExerciseStorage = olympiadExerciseStorage;
    }

    @GetMapping()
    public String levels(Model model){
        List<Level> levels = olympiadStorage.findAllLevels();

        model.addAttribute("levels",levels);
        return "admin/levels";
    }

    @PostMapping("/create-level")
    public ResponseEntity<Map<String,String>> createLevel(@RequestParam String name){
        Map<String,String> response = new HashMap<>();
        try{
            olympiadService.createLevel(name);

            response.put("message", "Уровень успешно создан!");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PostMapping("/delete-level")
    public ResponseEntity<Map<String,String>> deleteLevel(@RequestParam Long levelId){
        Map<String,String> response = new HashMap<>();
        try {
            olympiadStorage.deleteLevel(levelId);
            response.put("message", "");
            return ResponseEntity.ok(response);
        }catch (Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
