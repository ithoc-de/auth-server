package de.ithoc.auth.domain.consumer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ConsumerCreateRequestBody {

    private String username;
    private String password;

    private String realm;

    @JsonProperty("client_id")
    private String clientId;

    private String user;

}
