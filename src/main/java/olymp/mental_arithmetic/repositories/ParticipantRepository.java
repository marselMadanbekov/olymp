package olymp.mental_arithmetic.repositories;

import olymp.mental_arithmetic.model.entities.Participant;
import olymp.mental_arithmetic.model.entities.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Page<Participant> findByTour(Tour tour, Pageable pageable);
}
