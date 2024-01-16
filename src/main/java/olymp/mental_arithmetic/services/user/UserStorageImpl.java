package olymp.mental_arithmetic.services.user;

import olymp.mental_arithmetic.model.entities.Participant;
import olymp.mental_arithmetic.model.entities.Tour;
import olymp.mental_arithmetic.model.entities.User;
import olymp.mental_arithmetic.repositories.ParticipantRepository;
import olymp.mental_arithmetic.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserStorageImpl implements UserStorage {

    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;

    public UserStorageImpl(UserRepository userRepository, ParticipantRepository participantRepository) {
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public User getUserByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
        //:TODO add correct exception
    }

    @Override
    public void saveUser(User userData) {
        userRepository.save(userData);
    }

    @Override
    public void saveParticipant(Participant participant) {
        saveUser(participant.getUser());
        participantRepository.save(participant);
    }

    @Override
    public Page<Participant> findUsersPageByTour(Tour tour, Integer page) {
        Pageable pageable = PageRequest.of(page,20);
        return participantRepository.findByTour(tour,pageable);
    }

    @Override
    public Page<Participant> findAllParticipants(Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        return participantRepository.findAll(pageable);
    }

    @Override
    public Participant getParticipantById(Long participantId) {
        return participantRepository.findById(participantId).orElseThrow(() -> new RuntimeException("Участник не найден!"));
    }

    @Override
    public void deleteParticipantById(Long participantId) {
        Participant participant = getParticipantById(participantId);
        userRepository.delete(participant.getUser());
        participantRepository.delete(participant);
    }

}
