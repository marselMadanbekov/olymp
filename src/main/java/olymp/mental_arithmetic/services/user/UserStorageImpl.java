package olymp.mental_arithmetic.services.user;

import olymp.mental_arithmetic.model.entities.User;
import olymp.mental_arithmetic.repositories.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserStorageImpl implements UserStorage {

    private final UserRepository userRepository;

    public UserStorageImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден!"));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow();
        //:TODO add correct exception
    }

    @Override
    public void saveUser(User userData) {
        userRepository.save(userData);
    }

}
