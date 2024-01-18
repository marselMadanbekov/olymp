package olymp.mental_arithmetic.model.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TempUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @ManyToOne
    private Level level;
}
