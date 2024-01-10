package olymp.mental_arithmetic.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Olympiad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date startDate;
    @OneToMany
    private List<Tour> tours;

    private Double costOfParticipation;

    public int getTotalParticipantsCount(){
        return tours.stream()
                .mapToInt(Tour::countOfParticipants)
                .sum();
    }
}
