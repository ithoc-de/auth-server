package de.ithoc.auth.domain.consumer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Entity(name = "consumer")
@IdClass(ConsumerId.class)
public class ConsumerEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Id
    private String realm;

    @Id
    private String clientId;

    @Id
    private String username;

    private String apiKey;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ReadEntity> reads;

    public List<ReadEntity> getReads() {
        if(reads == null) {
            reads = new ArrayList<>();
        }
        return reads;
    }

}
