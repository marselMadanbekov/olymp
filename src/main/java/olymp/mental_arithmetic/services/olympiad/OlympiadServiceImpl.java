package olymp.mental_arithmetic.services.olympiad;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.Participant;
import olymp.mental_arithmetic.model.entities.Tour;
import olymp.mental_arithmetic.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OlympiadServiceImpl implements OlympiadService {

    private final OlympiadStorage olympiadStorage;
    private final UserStorage userStorage;

    @Autowired
    public OlympiadServiceImpl(OlympiadStorage olympiadStorage, UserStorage userStorage) {
        this.olympiadStorage = olympiadStorage;
        this.userStorage = userStorage;
    }

    @Override
    public void createOlympiad(String name, Double cost, String startDate) {
        olympiadStorage.saveOlympiad(Olympiad.builder()
                .name(name)
                .costOfParticipation(cost)
                .startDate(Date.valueOf(startDate))
                .build());
    }

    @Override
    public void createLevel(String name) {
        olympiadStorage.saveLevel(Level.builder()
                .name(name)
                .build());
    }

    @Override
    @Transactional
    public void createTour(Long olympiadId,String name, String startDateTime, String endDateTime) {
        Olympiad olympiad = olympiadStorage.getOlympiadById(olympiadId);

        Tour tour = new Tour();
        tour.setName(name);
        tour.setStartDateTime(LocalDateTime.parse(startDateTime));
        tour.setEndDateTime(LocalDateTime.parse(endDateTime));
        tour.setOlympiad(olympiad);
        olympiadStorage.saveTour(tour);
    }

    @Override
    public void removeParticipantFromTour(Long participantId) {
        Participant participant = userStorage.getParticipantById(participantId);
        participant.setTour(null);
        userStorage.saveParticipant(participant);
    }

    @Override
    public void addParticipantsToTourByLevel(Long tourId, Long levelId) {
        Tour tour = olympiadStorage.getTourById(tourId);
        Level level = olympiadStorage.getLevelById(levelId);

        List<Participant> participants = userStorage.findParticipantsByLevel(level);

        participants.forEach(participant -> {
            participant.setTour(tour);
        });

        userStorage.saveAllParticipants(participants);
    }
}
