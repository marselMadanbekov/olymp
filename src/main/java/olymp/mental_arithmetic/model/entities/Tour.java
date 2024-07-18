package olymp.mental_arithmetic.model.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @ManyToOne
    private Olympiad olympiad;
}
