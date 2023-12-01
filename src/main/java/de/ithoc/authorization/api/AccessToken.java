package de.ithoc.authorization.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private String tokenType; // 'Bearer' for machine-to-machine (M2M) applications

    @JsonProperty("expires_in")
    private Integer expiresIn; // 86400 seconds = 24 hours

}
