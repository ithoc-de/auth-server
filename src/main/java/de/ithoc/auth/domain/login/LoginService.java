package de.ithoc.auth.domain.login;

import de.ithoc.auth.domain.user.UserEntity;
import de.ithoc.auth.domain.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(String username, String password) {

        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        if (userEntity == null) {
            return false;
        }

        return passwordEncoder.matches(password, userEntity.getPassword());
    }

}
