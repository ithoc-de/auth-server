package de.ithoc.auth.domain.user;

import jakarta.annotation.PostConstruct;
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

    @PostConstruct
    public void init() {
        createUser(createUserDto("team-1a", "team-1a", false));
        createUser(createUserDto("team-1b", "team-1b", false));
        createUser(createUserDto("team-2a", "team-2a", false));
        createUser(createUserDto("team-2b", "team-2b", false));
        createUser(createUserDto("team-3a", "team-3a", false));
        createUser(createUserDto("team-3b", "team-3b", false));
        createUser(createUserDto("team-4", "team-4", false));
        createUser(createUserDto("team-5", "team-5", false));

        createUser(createUserDto("admin-1", "admin-1", true));
        createUser(createUserDto("admin-2", "admin-2", true));
        createUser(createUserDto("admin-3", "admin-3", true));
        createUser(createUserDto("admin-4", "admin-4", true));
        createUser(createUserDto("admin-5", "admin-5", true));
    }

    private UserDto createUserDto(String username, String password, boolean admin) {
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setAdmin(admin);
        return userDto;
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
