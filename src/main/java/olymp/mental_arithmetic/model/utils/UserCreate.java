package olymp.mental_arithmetic.model.utils;

import lombok.Data;

import java.sql.Date;

@Data
public class UserCreate {
    private String firstname;
    private String lastname;
    private String phoneNumber;
    private String email;
    private Date dateOfBirth;
    private String username;
    private String password;
    private Long levelId;
}
