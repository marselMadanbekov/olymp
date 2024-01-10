package olymp.mental_arithmetic.services.exercise;

import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.OlympiadExercise;
import olymp.mental_arithmetic.repositories.OlympiadExercisesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OlympiadExerciseStorageImpl implements OlympiadExerciseStorage{
    private final OlympiadExercisesRepository olympiadExercisesRepository;

    @Autowired
    public OlympiadExerciseStorageImpl(OlympiadExercisesRepository olympiadExercisesRepository) {
        this.olympiadExercisesRepository = olympiadExercisesRepository;
    }

    @Override
    public void save(OlympiadExercise olympiadExercise) {
        olympiadExercisesRepository.save(olympiadExercise);
    }

    @Override
    public OlympiadExercise findById(Long id) {
        return olympiadExercisesRepository.findById(id).orElseThrow(() -> new RuntimeException("Упражнение не найдено!"));
    }

    @Override
    public List<OlympiadExercise> findByOlympiad(Olympiad olympiad) {
        return olympiadExercisesRepository.findByOlympiad(olympiad);
    }
}
