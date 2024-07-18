package olymp.mental_arithmetic.services.user;

import olymp.mental_arithmetic.model.entities.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserStorage {

    User getUserByUsername(String name);

    User getUserById(Long id);

    void saveUser(User userData);

    void saveParticipant(Participant participant);

    Page<Participant> findUsersPageByTour(Tour tour, Integer page);

    Page<Participant> findAllParticipants(Integer page);

    Participant getParticipantById(Long participantId);

    void deleteParticipantById(Long participantId);

    void saveTempUser(TempUser tempUser);

    Page<TempUser> findTempUsersByPage(Integer page);

    TempUser getTempUserById(Long userId);

    void deleteTempUserById(Long id);

    void deleteUserById(Long id);

    List<Participant> findParticipantsByLevel(Level level);

    void saveAllParticipants(List<Participant> participants);
}
