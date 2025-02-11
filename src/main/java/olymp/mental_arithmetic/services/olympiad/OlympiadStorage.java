package olymp.mental_arithmetic.services.olympiad;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.Tour;

import java.util.List;

public interface OlympiadStorage {

    void saveOlympiad(Olympiad olympiad);
    Olympiad getOlympiadById(Long id);
    List<Olympiad> findAllOlympiads();


    void saveLevel(Level level);
    Level getLevelById(Long id);
    List<Level> findAllLevels();
    void deleteLevel(Long levelId);

    void saveTour(Tour tour);
    Tour getTourById(Long id);
    void deleteTourById(Long id);

    List<Tour> findAllToursByOlympiad(Olympiad olympiad);

    List<Tour> findAllTours();
}
