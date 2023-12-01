package de.ithoc.auth.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity(name = "api_key")
public class ApiKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID apiKey;

    @OneToOne
    private ClientEntity client;

    @OneToOne
    private AudienceEntity audience;

}
