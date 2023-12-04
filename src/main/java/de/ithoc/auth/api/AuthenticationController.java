package de.ithoc.auth.api;

import de.ithoc.auth.model.BearerToken;
import de.ithoc.auth.model.GrantType;
import de.ithoc.auth.services.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/token")
    public ResponseEntity<AccessToken> token(@RequestBody TokenBody tokenBody) {
        log.trace("token called");

        GrantType grantType = GrantType.valueOf(tokenBody.getGrantType().toUpperCase());
        BearerToken bearerToken = authenticationService.fetchToken(
                grantType,
                tokenBody.getClientId(),
                tokenBody.getClientSecret(),
                tokenBody.getAudience());
        if(bearerToken == null) {
            return ResponseEntity.status(401).build();
        }

        AccessToken accessToken = new AccessToken();
        accessToken.setAccessToken(bearerToken.getJwt());
        accessToken.setTokenType(bearerToken.getTokenType());
        accessToken.setExpiresIn(bearerToken.getExpiresIn());

        return ResponseEntity.ok(accessToken);
    }

}
