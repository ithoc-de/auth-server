package de.ithoc.auth.domain.consumer;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class ConsumerId implements Serializable {

    private String realm;
    private String clientId;
    private String username;

}
