package de.ithoc.auth.domain.login;

import de.ithoc.auth.domain.user.UserDto;
import de.ithoc.auth.domain.user.UserEntity;
import de.ithoc.auth.domain.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;


    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public UserDto login(String username, String password) {

        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        if (userEntity == null) {
            return null;
        }

        if(!passwordEncoder.matches(password, userEntity.getPassword())) {
            return null;
        }

        return modelMapper.map(userEntity, UserDto.class);
    }


}
