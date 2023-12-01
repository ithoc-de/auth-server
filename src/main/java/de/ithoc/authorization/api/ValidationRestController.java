package de.ithoc.authorization.api;

import de.ithoc.authorization.services.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ValidationRestController {

    private final AuthService authService;

    public ValidationRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/validation")
    public ResponseEntity<Void> validation(@RequestBody ValidationBody validationBody) {
        log.trace("validation called");

        Boolean valid = authService.validation(validationBody.getAccessToken());
        if(!valid) {
            return ResponseEntity.status(401).build();
        }

        return ResponseEntity.ok().build();
    }

}
