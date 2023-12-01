package de.ithoc.authorization;

import de.ithoc.authorization.persistance.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.UUID;

@SpringBootApplication
public class AuthorizationServerApplication {

    @Value("${authorization.server.bcrypt.salt}")
    private String bcryptSalt;

    private final AudienceRepository audienceRepository;
    private final ClientRepository clientRepository;
    private final ApiKeyRepository apiKeyRepository;

    public AuthorizationServerApplication(AudienceRepository audienceRepository,
                                          ClientRepository clientRepository,
                                          ApiKeyRepository apiKeyRepository) {
        this.audienceRepository = audienceRepository;
        this.clientRepository = clientRepository;
        this.apiKeyRepository = apiKeyRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthorizationServerApplication.class, args);
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
