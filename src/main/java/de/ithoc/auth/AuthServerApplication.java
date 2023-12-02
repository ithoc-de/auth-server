package de.ithoc.auth;

import de.ithoc.auth.persistance.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
public class AuthServerApplication {

    @Value("${authorization.server.bcrypt.salt}")
    private String bcryptSalt;

    private final AudienceRepository audienceRepository;
    private final ClientRepository clientRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final TokenRepository tokenRepository;

    public AuthServerApplication(AudienceRepository audienceRepository,
                                 ClientRepository clientRepository,
                                 ApiKeyRepository apiKeyRepository, TokenRepository tokenRepository) {
        this.audienceRepository = audienceRepository;
        this.clientRepository = clientRepository;
        this.apiKeyRepository = apiKeyRepository;
        this.tokenRepository = tokenRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @PostConstruct
    public void init() {

        // Web Blog REST API Java
        AudienceEntity audienceEntity = new AudienceEntity();
        audienceEntity.setAudienceId("web-blog-rest-api-java");
        audienceRepository.save(audienceEntity);

        createClient("team-1", "SlVg0iGOhEXkOOZwlvw6", audienceEntity);
        createClient("team-2", "SlVg0iGOhEXkOOZwlvw6", audienceEntity);
        createClient("team-3", "SlVg0iGOhEXkOOZwlvw6", audienceEntity);
        createClient("team-4", "SlVg0iGOhEXkOOZwlvw6", audienceEntity);

        ClientEntity app1 = createClient("app-1", "SlVg0iGOhEXkOOZwlvw6", audienceEntity);

        UUID uuid = UUID.fromString("d750c51a-d354-4e6d-ad50-b532946d3d11");
        ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setApiKey(uuid);
        apiKeyEntity.setClient(app1);
        apiKeyEntity.setAudience(audienceEntity);
        apiKeyRepository.save(apiKeyEntity);

        String token = "eyJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJJdGhvYyIsInN1YiI6ImFwcC0xIiwiZXhwIjoxNzAxNTE5NjE5LCJqdGkiOiJzY29wZSJ9._yR5URhU6fvf2xwvj9vDQ2ahzARgxwNEYSpisjlQQFbfXkcn61v8WJyVAccGQwPe";
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setAccessToken(token);
        tokenEntity.setClient(app1);
        tokenEntity.setAudience(audienceEntity);
        tokenEntity.setExpiresIn(7200);
        tokenEntity.setTokenType("Bearer");
        tokenEntity.setCreatedAt(LocalDateTime.now());
        tokenRepository.save(tokenEntity);
    }

    private ClientEntity createClient(String clientId,
                              @SuppressWarnings("SameParameterValue") String clientSecret,
                              AudienceEntity audienceEntity) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(clientId);
        clientEntity.setClientSecret(BCrypt.hashpw(clientSecret, bcryptSalt));
        clientRepository.save(clientEntity);
        clientEntity.getAudiences().add(audienceEntity);
        clientRepository.save(clientEntity);

        return clientEntity;
    }

}
