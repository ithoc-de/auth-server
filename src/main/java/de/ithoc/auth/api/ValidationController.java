package de.ithoc.auth.api;

import de.ithoc.auth.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is used to validate tokens. CORS policy needs to stay enabled,
 * as it will only be called from resource servers, so backend to backend.
 */
@Slf4j
@RestController
public class ValidationController {

    private final AuthenticationService authenticationService;

    public ValidationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/validation")
    public ResponseEntity<ValidationResponseBody> validation(
            @RequestHeader("x-api-key") String apiKey,
            @RequestBody ValidationBody validationBody) {
        log.trace("validation called");

        /*
         * First, check if the API key is valid, meaning the client is registered and
         * has permission to validate tokens at all.
         */
        if(!authenticationService.validateApiKey(apiKey)) {
            ValidationResponseBody validationResponseBody =
                    getValidationResponseBody(false, 1, "API key is invalid");
            return ResponseEntity.status(401).body(validationResponseBody);
        }

        /*
         * Second, check if the token is valid, meaning the client is registered and
         * has access to the API.
         */
        if(!authenticationService.validateToken(validationBody.getAccessToken())) {
            ValidationResponseBody validationResponseBody =
                    getValidationResponseBody(false, 2, "Token is invalid");
            return ResponseEntity.status(401).body(validationResponseBody);
        }

        return ResponseEntity.ok(getValidationResponseBody(true, 0, "Request valid"));
    }

    private static ValidationResponseBody getValidationResponseBody(boolean valid, int code, String message) {
        ValidationResponseBody validationResponseBody = new ValidationResponseBody();
        validationResponseBody.setValid(valid);
        validationResponseBody.setCode(code);
        validationResponseBody.setMessage(message);
        return validationResponseBody;
    }

}
