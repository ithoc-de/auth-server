package de.ithoc.auth.domain.user;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class UserController implements UsersApi {

    private final UserService userService;
    private final ModelMapper modelMapper;


    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @Override
    public ResponseEntity<Void> createUser(@RequestBody UserRequestBody user) {

        UserDto userDto = modelMapper.map(user, UserDto.class);
        userService.createUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<UserResponseBody> getUserByUsername(String username) {

        UserDto userDto = userService.readUser(username);
        UserResponseBody userResponseBody = modelMapper.map(userDto, UserResponseBody.class);

        return ResponseEntity.ok(userResponseBody);
    }

}
