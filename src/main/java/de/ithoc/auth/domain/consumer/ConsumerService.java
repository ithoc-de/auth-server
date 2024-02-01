package de.ithoc.auth.domain.consumer;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ConsumerService {

    private final ConsumerRepository consumerRepository;

    public ConsumerService(ConsumerRepository consumerRepository) {
        this.consumerRepository = consumerRepository;
    }


    @PostConstruct
    public void init() {

        generate("f73-gen4-jfs", "account-service", "team-1a");
        generate("f73-gen4-jfs", "account-service", "team-1b");
        generate("f73-gen4-jfs", "inventory-service", "team-2a");
        generate("f73-gen4-jfs", "inventory-service", "team-2b");
        generate("f73-gen4-jfs", "lending-service", "team-3a");
        generate("f73-gen4-jfs", "lending-service", "team-3b");
        generate("f73-gen4-jfs", "service-registry", "team-4");
        generate("f73-gen4-jfs", "frontend", "team-5");
    }

    /**
     * Reads the API key for a given client ID and username and sets the timestamp for this read.
     * @param clientId ID of the client
     * @param username Username of the user
     * @return API key
     * @throws NoSuchElementException If client or user does not exist
     */
    public String read(String realm, String clientId, String username) throws NoSuchElementException {

        ConsumerEntity consumerEntity = consumerRepository
                .findByRealmAndClientIdAndUsername(realm, clientId, username)
                .orElseThrow(NoSuchElementException::new);

        ReadEntity readEntity = new ReadEntity();
        readEntity.setTimestamp(new Timestamp(System.currentTimeMillis()));
        consumerEntity.getReads().add(readEntity);
        consumerRepository.save(consumerEntity);

        return consumerEntity.getApiKey();
    }

    /**
     * Generates and stores an API key.
     *
     * @param clientId ID of the client
     * @param username Username of the user
     * @throws NoSuchElementException   If client or audience does not exist
     * @throws IllegalArgumentException If client secret is wrong
     */
    public void generate(String realm, String clientId, String username)
            throws NoSuchElementException, IllegalArgumentException {

        ConsumerEntity consumerEntity = new ConsumerEntity();
        consumerEntity.setRealm(realm);
        consumerEntity.setClientId(clientId);
        consumerEntity.setUsername(username);

        // Generate API key that is a UUID for now.
        String apiKey = UUID.randomUUID().toString();
        consumerEntity.setApiKey(apiKey);

        consumerRepository.save(consumerEntity);
    }

}
