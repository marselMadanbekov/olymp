package olymp.mental_arithmetic.services.olympiad;

public interface OlympiadService {
    void createOlympiad(String name, Double cost, String startDate);
    void createLevel(String name);
}
