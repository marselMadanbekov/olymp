package olymp.mental_arithmetic.services.olympiad;

public interface OlympiadService {
    void createOlympiad(String name, Double cost, String startDate);
    void createLevel(String name);

    void createTour(Long olympiadId,String name, String startDateTime, String endDateTime);

    void removeParticipantFromTour(Long participantId);

    void addParticipantsToTourByLevel(Long tourId, Long levelId);
}
