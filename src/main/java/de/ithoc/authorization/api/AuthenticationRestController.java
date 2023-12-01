package de.ithoc.authorization.api;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
public class AuthenticationRestController {

    @PostMapping(value = "/token", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccessToken> token(TokenBody tokenBody) {
        log.trace("token called");

        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(UUID.randomUUID().toString());
        accessToken.setTokenType("Bearer");
        accessToken.setExpiresIn(86400);

        return ResponseEntity.ok(accessToken);
    }

    @PostConstruct
    public void init() {
        log.info("AuthenticationRestController initialized");


    }
}
