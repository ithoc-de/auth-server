package de.ithoc.auth.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AudienceRepository extends JpaRepository<AudienceEntity, UUID> {
}
