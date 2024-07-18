package olymp.mental_arithmetic.services.user;

import olymp.mental_arithmetic.model.entities.*;
import olymp.mental_arithmetic.repositories.ParticipantRepository;
import olymp.mental_arithmetic.repositories.TempUserRepository;
import olymp.mental_arithmetic.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStorageImpl implements UserStorage {

    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final TempUserRepository tempUserRepository;
    public UserStorageImpl(UserRepository userRepository, ParticipantRepository participantRepository, TempUserRepository tempUserRepository) {
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
        this.tempUserRepository = tempUserRepository;
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
    }

    @Override
    public void saveTempUser(TempUser tempUser) {
        tempUserRepository.save(tempUser);
    }

    @Override
    public Page<TempUser> findTempUsersByPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 20);
        return tempUserRepository.findAll(pageable);
    }

    @Override
    public TempUser getTempUserById(Long userId) {
        return tempUserRepository.findById(userId).orElseThrow(() -> new RuntimeException("Пользователь не найден!"));
    }

    @Override
    public void deleteTempUserById(Long id) {
        tempUserRepository.deleteById(id);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<Participant> findParticipantsByLevel(Level level) {
        return participantRepository.findByLevel(level);
    }

    @Override
    public void saveAllParticipants(List<Participant> participants) {
        participantRepository.saveAll(participants);
    }

}
