package olymp.mental_arithmetic.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Tour tour;
    @ManyToOne
    private Level level;


    @OneToOne
    private User user;
}
