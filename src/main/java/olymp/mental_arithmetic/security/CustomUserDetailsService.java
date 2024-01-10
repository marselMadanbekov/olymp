package olymp.mental_arithmetic.security;

import olymp.mental_arithmetic.model.entities.User;
import olymp.mental_arithmetic.services.user.UserStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserStorage userStorage;

    @Autowired
    public CustomUserDetailsService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return build(userStorage.getUserByUsername(username));
    }

    public User loadUserById(Long id) {
        return build(userStorage.getUserById(id));
    }

    private User build(User userData) {

        return User.builder()
                .id(userData.getId())
                .username(userData.getUsername())
                .password(userData.getPassword())
                .role(userData.getRole())
                .active(userData.getActive())
                .build();
    }
}
