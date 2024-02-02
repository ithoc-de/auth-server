package de.ithoc.auth.domain.user;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    public void createUser(UserDto userDto) {

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(userEntity);
    }

    public UserDto readUser(String username) throws NoSuchElementException {

        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow();
        UserDto userDto = modelMapper.map(userEntity, UserDto.class);
        userDto.setPassword(null);

        return userDto;
    }

}
