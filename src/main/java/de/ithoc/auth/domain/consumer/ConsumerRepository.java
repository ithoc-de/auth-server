package de.ithoc.auth.domain.consumer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ConsumerRepository extends JpaRepository<ConsumerEntity, UUID> {

        Optional<ConsumerEntity> findByRealmAndClientIdAndUsername(String realm, String clientId, String username);

}
