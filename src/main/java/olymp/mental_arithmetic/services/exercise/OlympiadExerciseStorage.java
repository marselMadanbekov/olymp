package olymp.mental_arithmetic.services.exercise;

import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.OlympiadExercise;

import java.util.List;

public interface OlympiadExerciseStorage {
    void save(OlympiadExercise olympiadExercise);
    OlympiadExercise findById(Long id);
    List<OlympiadExercise> findByOlympiad(Olympiad olympiad);
}
