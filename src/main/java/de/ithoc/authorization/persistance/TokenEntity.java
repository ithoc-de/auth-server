package de.ithoc.authorization.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String accessToken;
    private String tokenType;
    private LocalDateTime createdAt;
    private Integer expiresIn;

    @OneToOne
    private ClientEntity client;

    @OneToOne
    private AudienceEntity audience;

}
