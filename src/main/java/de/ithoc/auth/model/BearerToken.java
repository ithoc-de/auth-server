package de.ithoc.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BearerToken {

    private String jwt;
    private String tokenType;
    private int expiresIn;

}
