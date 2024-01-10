package olymp.mental_arithmetic.services.exercise;

import olymp.mental_arithmetic.repositories.MentalTaskRepository;
import olymp.mental_arithmetic.services.MentalExercisesExcelResolver;
import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.MentalTask;
import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.OlympiadExercise;
import olymp.mental_arithmetic.repositories.OlympiadExercisesRepository;
import olymp.mental_arithmetic.services.olympiad.OlympiadStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class OlympiadExerciseServiceImpl implements OlympiadExerciseService {

    private final OlympiadExercisesRepository olympiadExercisesRepository;
    private final MentalTaskRepository mentalTaskRepository;
    private final OlympiadStorage olympiadStorage;

    private final MentalExercisesExcelResolver resolver;

    @Value("${uploads_path}")
    private String UPLOADED_FOLDER;

    @Autowired
    public OlympiadExerciseServiceImpl(OlympiadExercisesRepository olympiadExercisesRepository, MentalTaskRepository mentalTaskRepository, OlympiadStorage olympiadStorage, MentalExercisesExcelResolver resolver) {
        this.olympiadExercisesRepository = olympiadExercisesRepository;
        this.mentalTaskRepository = mentalTaskRepository;
        this.olympiadStorage = olympiadStorage;
        this.resolver = resolver;
    }

    @Override
    @Transactional
    public void createExercise(Long olympiadId, Long levelId, MultipartFile excel) {
        String filename;
        try {
            byte[] bytes = excel.getBytes();
            filename = excel.getOriginalFilename();
            Path path = Paths.get(UPLOADED_FOLDER + "/" + excel.getOriginalFilename());
            File uploadDire = new File(UPLOADED_FOLDER);
            if (!uploadDire.exists())
                uploadDire.mkdirs();
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Ошибка при загрузке файла");
        }

        Olympiad olympiad = olympiadStorage.getOlympiadById(olympiadId);
        Level level = olympiadStorage.getLevelById(levelId);
        OlympiadExercise exercise = new OlympiadExercise();
        exercise.setOlympiad(olympiad);
        exercise.setTargetLevel(level);
        exercise.setExcelFilename(filename);

        List<MentalTask> tasks = resolver.pullMentalTasksFromExcel(filename);
        mentalTaskRepository.saveAll(tasks);
        exercise.setExercise(tasks);

        olympiadExercisesRepository.save(exercise);
    }
}
