package olymp.mental_arithmetic.repositories;

import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.OlympiadExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OlympiadExercisesRepository extends JpaRepository<OlympiadExercise, Long> {
    List<OlympiadExercise> findByOlympiad(Olympiad olympiad);
}
