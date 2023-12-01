package de.ithoc.auth.services;

import de.ithoc.auth.model.BearerToken;
import de.ithoc.auth.model.GrantType;
import de.ithoc.auth.persistance.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Service
public class AuthService {

    @Value("${authorization.server.jwt.issuer}")
    private String jwtIssuer;

    @Value("${authorization.server.jwt.expiration}")
    private String jwtExpiration;

    @Value("${authorization.server.jwt.key}")
    private String jwtKey;

    @Value("${authorization.server.bcrypt.salt}")
    private String bcryptSalt;

    private final ClientRepository clientRepository;
    private final TokenRepository tokenRepository;

    public AuthService(ClientRepository clientRepository, TokenRepository tokenRepository) {
        this.clientRepository = clientRepository;
        this.tokenRepository = tokenRepository;
    }

    public Boolean validation(String token) {

        return tokenRepository.findByAccessToken(token).isPresent();
    }

    public BearerToken bearerToken(GrantType grantType, String clientId, String clientSecret, String audience) {

        String token = null;
        if (grantType == GrantType.CLIENT_CREDENTIALS) {
            log.info("client_credentials grant type");

            clientSecret = BCrypt.hashpw(clientSecret, bcryptSalt);
            token = clientRepository.findByClientIdAndClientSecret(clientId, clientSecret)
                    .map(clientEntity -> {
                        log.info("client found");

                        return clientEntity.getAudiences().stream()
                                .filter(audienceEntity -> audienceEntity.getAudienceId().equals(audience))
                                .findFirst().map(audienceEntity -> {
                                    log.info("audience found");

                                    // Generate the JWT
                                    LocalDateTime now = LocalDateTime.now();
                                    int expiresIn = Integer.parseInt(jwtExpiration);
                                    LocalDateTime expiresAt = now.plusSeconds(expiresIn);
                                    Date expiration = Date.from(expiresAt.atZone(ZoneId.systemDefault()).toInstant());
                                    String jwt = generateJwt(clientId, expiration);

                                    // Create the token on database
                                    createToken(clientEntity, audienceEntity, jwt, now);
                                    return jwt;

                                }).orElse(null);
                    }).orElse(null);
        }

        BearerToken bearerToken = null;
        if(token != null) {
            bearerToken = new BearerToken();
            bearerToken.setJwt(token);
            bearerToken.setTokenType("Bearer");
            bearerToken.setExpiresIn(Integer.parseInt(jwtExpiration));
        }

        return bearerToken;
    }

    private void createToken(ClientEntity clientEntity,
                             AudienceEntity audienceEntity,
                             String jwt,
                             LocalDateTime createdAt) {

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setClient(clientEntity);
        tokenEntity.setAudience(audienceEntity);
        tokenEntity.setTokenType("Bearer");
        tokenEntity.setAccessToken(jwt);
        tokenEntity.setCreatedAt(createdAt);
        tokenEntity.setExpiresIn(Integer.parseInt(jwtExpiration));

        tokenRepository.findByClientAndAudience(clientEntity, audienceEntity)
                .ifPresent(tokenRepository::delete);
        tokenRepository.save(tokenEntity);
    }

    private String generateJwt(String clientId, Date expiresAt) {
        byte[] decode = Decoders.BASE64.decode(jwtKey);
        Key key = Keys.hmacShaKeyFor(decode);

        return Jwts.builder()
                .issuer(jwtIssuer)
                .subject(clientId)
                .expiration(expiresAt)
                .id("scope")
                .signWith(key)
                .compact();
    }

}
