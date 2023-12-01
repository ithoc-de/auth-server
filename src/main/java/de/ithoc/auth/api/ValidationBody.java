package de.ithoc.auth.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationBody {

    @JsonProperty("access_token")
    private String accessToken;

}
