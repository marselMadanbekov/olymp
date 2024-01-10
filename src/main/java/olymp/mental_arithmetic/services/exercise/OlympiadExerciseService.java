package olymp.mental_arithmetic.services.exercise;

import org.springframework.web.multipart.MultipartFile;

public interface OlympiadExerciseService {
    void createExercise(Long olympiadId, Long levelId, MultipartFile excel);
}
