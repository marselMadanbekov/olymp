package olymp.mental_arithmetic.repositories;

import olymp.mental_arithmetic.model.entities.Olympiad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OlympiadRepository extends JpaRepository<Olympiad, Long> {
}
