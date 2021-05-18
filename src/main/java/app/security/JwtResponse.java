package app.security;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtResponse {
    @JsonProperty(value = "token", required = true)
    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "{" +
            " token='" + this.token + "'" +
            "}";
    }
}
