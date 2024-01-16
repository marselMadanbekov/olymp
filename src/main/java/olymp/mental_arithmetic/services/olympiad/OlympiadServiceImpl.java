package olymp.mental_arithmetic.services.olympiad;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Olympiad;
import olymp.mental_arithmetic.model.entities.Tour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;

@Service
public class OlympiadServiceImpl implements OlympiadService {

    private final OlympiadStorage olympiadStorage;

    @Autowired
    public OlympiadServiceImpl(OlympiadStorage olympiadStorage) {
        this.olympiadStorage = olympiadStorage;
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
    public void createTour(Long olympiadId, String startDateTime, String endDateTime) {
        Olympiad olympiad = olympiadStorage.getOlympiadById(olympiadId);

        Tour tour = new Tour();
        tour.setStartDateTime(LocalDateTime.parse(startDateTime));
        tour.setEndDateTime(LocalDateTime.parse(endDateTime));
        tour.setOlympiad(olympiad);
        olympiadStorage.saveTour(tour);
    }
}
