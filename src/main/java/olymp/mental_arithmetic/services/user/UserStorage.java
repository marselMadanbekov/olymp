package olymp.mental_arithmetic.services.user;

import olymp.mental_arithmetic.model.entities.User;

public interface UserStorage {

    User getUserByUsername(String name);

    User getUserById(Long id);

    void saveUser(User userData);

}
