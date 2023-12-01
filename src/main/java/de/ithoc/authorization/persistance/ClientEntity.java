package de.ithoc.authorization.persistance;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String clientId;
    private String clientSecret;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AudienceEntity> audiences;

}
