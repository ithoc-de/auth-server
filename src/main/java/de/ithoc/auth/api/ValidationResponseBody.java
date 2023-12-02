package de.ithoc.auth.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationResponseBody {

    private boolean valid;
    private int code;
    private String message;

}
