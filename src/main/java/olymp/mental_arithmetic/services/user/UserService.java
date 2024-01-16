package olymp.mental_arithmetic.services.user;

import olymp.mental_arithmetic.model.utils.UserCreate;
import olymp.mental_arithmetic.model.utils.UserdataUpdate;

public interface UserService {
    void createParticipant(UserCreate userCreate);

    void updateUserdata(UserdataUpdate update);

    void deleteParticipantById(Long participantId);
}
