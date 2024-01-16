package olymp.mental_arithmetic.services.user;

import olymp.mental_arithmetic.model.entities.Participant;
import olymp.mental_arithmetic.model.entities.Tour;
import olymp.mental_arithmetic.model.entities.User;
import org.springframework.data.domain.Page;

public interface UserStorage {

    User getUserByUsername(String name);

    User getUserById(Long id);

    void saveUser(User userData);

    void saveParticipant(Participant participant);

    Page<Participant> findUsersPageByTour(Tour tour, Integer page);

    Page<Participant> findAllParticipants(Integer page);

    Participant getParticipantById(Long participantId);

    void deleteParticipantById(Long participantId);
}
