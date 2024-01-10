package olymp.mental_arithmetic.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class OlympiadExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Level targetLevel;
    private String excelFilename;
    @ManyToOne
    private Olympiad olympiad;

    @OneToMany
    private List<MentalTask> exercise;
}
