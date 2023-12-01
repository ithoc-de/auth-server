package de.ithoc.auth.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenBody {

    @JsonProperty("grant_type")
    private String grantType; // 'client_credentials' for machine-to-machine (M2M) applications

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    @JsonProperty("audience")
    private String audience; // API Identifier

}
