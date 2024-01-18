package olymp.mental_arithmetic.repositories;

import olymp.mental_arithmetic.model.entities.TempUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempUserRepository extends JpaRepository<TempUser, Long> {
}
