package olymp.mental_arithmetic.controllers;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.services.olympiad.OlympiadStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping("/public")
public class PublicController {

    private final OlympiadStorage olympiadStorage;

    @Autowired
    public PublicController(OlympiadStorage olympiadStorage) {
        this.olympiadStorage = olympiadStorage;
    }

    @GetMapping
    public String mainPage(Model model){
        List<Level> levels = olympiadStorage.findAllLevels();
        model.addAttribute("levels",levels);
        return "public/index";
    }
}
