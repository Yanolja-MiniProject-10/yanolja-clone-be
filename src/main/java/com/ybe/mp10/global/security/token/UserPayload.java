package com.ybe.mp10.global.security.token;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.AuthenticatedPrincipal;

@JsonIgnoreProperties({"name"})
public record UserPayload(Long id, String email) implements AuthenticatedPrincipal {

    public UserPayload(@JsonProperty("id") Long id, @JsonProperty("email") String email) {
        this.id = id;
        this.email = email;
    }

    @Override
    public String getName() {
        return this.email;
    }
}
