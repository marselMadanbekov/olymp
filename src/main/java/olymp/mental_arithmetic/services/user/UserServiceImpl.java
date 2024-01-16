package olymp.mental_arithmetic.services.user;

import olymp.mental_arithmetic.model.entities.Level;
import olymp.mental_arithmetic.model.entities.Participant;
import olymp.mental_arithmetic.model.entities.User;
import olymp.mental_arithmetic.model.utils.UserCreate;
import olymp.mental_arithmetic.model.utils.UserdataUpdate;
import olymp.mental_arithmetic.services.olympiad.OlympiadStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserStorage userStorage;
    private final OlympiadStorage olympiadStorage;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserStorage userStorage, OlympiadStorage olympiadStorage, PasswordEncoder passwordEncoder) {
        this.userStorage = userStorage;
        this.olympiadStorage = olympiadStorage;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void createParticipant(UserCreate userCreate) {


        Level level = userCreate.getLevelId().equals(0L) ? null : olympiadStorage.getLevelById(userCreate.getLevelId());

        User user = new User();
        user.setFirstname(userCreate.getFirstname());
        user.setLastname(userCreate.getLastname());
        user.setEmail(userCreate.getEmail());
        user.setActive(true);
        user.setPhone(userCreate.getPhoneNumber());
        user.setUsername(userCreate.getUsername());
        user.setPassword(passwordEncoder.encode(userCreate.getPassword()));

        userStorage.saveUser(user);

        Participant participant = new Participant();
        participant.setUser(user);
        participant.setLevel(level);

        userStorage.saveParticipant(participant);
    }

    @Override
    public void updateUserdata(UserdataUpdate update) {
        User userData = userStorage.getUserById(update.getUserdataId());


        if (!update.getFirstname().isEmpty())
            userData.setFirstname(update.getFirstname());

        if (!update.getLastname().isEmpty())
            userData.setLastname(update.getLastname());

        if (!update.getPhoneNumber().isEmpty())
            userData.setPhone(update.getPhoneNumber());

        if (!update.getEmail().isEmpty())
            userData.setEmail(update.getEmail());

        if (!update.getPassword().isEmpty()) {
            if (!update.getPassword().equals(update.getConfirmPassword()))
                throw new RuntimeException("Пароль и подтверждение не совпадают!");
            userData.setPassword(passwordEncoder.encode(update.getPassword()));
        }

        userStorage.saveUser(userData);
    }

    @Override
    public void deleteParticipantById(Long participantId) {
        userStorage.deleteParticipantById(participantId);
    }
}
