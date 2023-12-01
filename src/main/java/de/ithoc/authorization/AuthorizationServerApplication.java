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

        for (int i = 0; i < 3; i++) {
            AudienceEntity audienceEntity = new AudienceEntity();
            audienceEntity.setAudienceId("api-" + i);
            audienceRepository.save(audienceEntity);

        }

        for (int j = 0; j < 3; j++) {
            ClientEntity clientEntity = new ClientEntity();
            clientEntity.setClientId("client-" + j);
            clientEntity.setClientSecret(BCrypt.hashpw("secret-" + j, bcryptSalt));
            clientRepository.save(clientEntity);
            clientEntity.setAudiences(audienceRepository.findAll());
            clientRepository.save(clientEntity);
        }
    }

}
