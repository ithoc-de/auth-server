package de.ithoc.authorization.persistance;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String accessToken;
    private String tokenType;
    private Integer expiresIn;
    private LocalDateTime createdAt;

    @OneToOne
    private ClientEntity client;

    @OneToOne
    private AudienceEntity audience;

}
