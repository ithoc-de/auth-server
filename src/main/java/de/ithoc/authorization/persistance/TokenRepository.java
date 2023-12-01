package de.ithoc.authorization.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TokenRepository extends JpaRepository<TokenEntity, UUID> {

    Optional<TokenEntity> findByClientAndAudience(ClientEntity client, AudienceEntity audience);

    Optional<TokenEntity> findByAccessToken(String accessToken);

}
