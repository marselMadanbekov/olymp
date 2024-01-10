package olymp.mental_arithmetic.services.olympiad;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.repositories.LevelRepository;
import olymp.mental_arithmetic.repositories.OlympiadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OlympiadStorageImpl implements OlympiadStorage {

    private final OlympiadRepository olympiadRepository;
    private final LevelRepository levelRepository;

    @Autowired
    public OlympiadStorageImpl(OlympiadRepository olympiadRepository, LevelRepository levelRepository) {
        this.olympiadRepository = olympiadRepository;
        this.levelRepository = levelRepository;
    }

    @Override
    public void saveOlympiad(Olympiad olympiad) {
        olympiadRepository.save(olympiad);
    }

    @Override
    public Olympiad getOlympiadById(Long id) {
        return olympiadRepository.findById(id).orElseThrow(() -> new RuntimeException("Олимпиада не найдена!"));
    }

    @Override
    public List<Olympiad> findAllOlympiads() {
        return olympiadRepository.findAll();
    }

    @Override
    public void saveLevel(Level level) {
        levelRepository.save(level);
    }

    @Override
    public Level getLevelById(Long id) {
        return levelRepository.findById(id).orElseThrow(() -> new RuntimeException("Уровень не найден!"));
    }

    @Override
    public List<Level> findAllLevels() {
        return levelRepository.findAll();
    }

    @Override
    public void deleteLevel(Long levelId) {
        levelRepository.deleteById(levelId);
    }
}
