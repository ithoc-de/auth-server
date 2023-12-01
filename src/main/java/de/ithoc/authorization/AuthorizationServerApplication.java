package de.ithoc.authorization;

import de.ithoc.authorization.persistance.AudienceEntity;
import de.ithoc.authorization.persistance.AudienceRepository;
import de.ithoc.authorization.persistance.ClientEntity;
import de.ithoc.authorization.persistance.ClientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCrypt;

@SpringBootApplication
public class AuthorizationServerApplication {

    @Value("${authorization.server.bcrypt.salt}")
    private String bcryptSalt;

    private final AudienceRepository audienceRepository;
    private final ClientRepository clientRepository;

    public AuthorizationServerApplication(AudienceRepository audienceRepository, ClientRepository clientRepository) {
        this.audienceRepository = audienceRepository;
        this.clientRepository = clientRepository;
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
    }

    private void createClient(String clientId,
                              @SuppressWarnings("SameParameterValue") String clientSecret,
                              AudienceEntity audienceEntity) {
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setClientId(clientId);
        clientEntity.setClientSecret(BCrypt.hashpw(clientSecret, bcryptSalt));
        clientRepository.save(clientEntity);
        clientEntity.getAudiences().add(audienceEntity);
        clientRepository.save(clientEntity);
    }

}
