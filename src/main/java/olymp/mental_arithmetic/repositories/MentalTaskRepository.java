package olymp.mental_arithmetic.repositories;

import olymp.mental_arithmetic.model.entities.MentalTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentalTaskRepository extends JpaRepository<MentalTask, Long> {
}
