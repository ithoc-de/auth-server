package de.ithoc.auth.domain.consumer;

import de.ithoc.auth.domain.login.LoginService;
import de.ithoc.auth.domain.user.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/consumers")
@CrossOrigin(origins = "*")
public class ConsumerController {

    private final ConsumerService consumerService;
    private final LoginService loginService;

    public ConsumerController(ConsumerService consumerService, LoginService loginService) {
        this.consumerService = consumerService;
        this.loginService = loginService;
    }

    @PostMapping("/fetch")
    public ResponseEntity<ConsumerFetchResponseBody> fetch(@RequestBody ConsumerFetchRequestBody consumerFetchRequestBody) {
        log.trace(consumerFetchRequestBody.toString());

        UserDto userDto = loginService.login(
                consumerFetchRequestBody.getUsername(), consumerFetchRequestBody.getPassword());
        if(userDto == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String apiKey = consumerService.read(
                consumerFetchRequestBody.getRealm(),
                consumerFetchRequestBody.getClientId(),
                consumerFetchRequestBody.getUsername()
        );

        ConsumerFetchResponseBody consumerFetchResponseBody = new ConsumerFetchResponseBody();
        consumerFetchResponseBody.setApiKey(apiKey);

        return ResponseEntity.ok(consumerFetchResponseBody);
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody ConsumerCreateRequestBody consumerCreateRequestBody) {
        log.trace(consumerCreateRequestBody.toString());

        UserDto userDto = loginService.login(
                consumerCreateRequestBody.getUsername(), consumerCreateRequestBody.getPassword());
        if(userDto == null || !userDto.isAdmin()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        consumerService.generate(
                consumerCreateRequestBody.getRealm(),
                consumerCreateRequestBody.getClientId(),
                consumerCreateRequestBody.getUser()
        );

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

