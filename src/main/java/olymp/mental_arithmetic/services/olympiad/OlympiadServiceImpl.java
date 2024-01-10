package olymp.mental_arithmetic.services.olympiad;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Olympiad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

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
}
