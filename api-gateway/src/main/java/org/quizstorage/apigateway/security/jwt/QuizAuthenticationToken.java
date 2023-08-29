package org.quizstorage.apigateway.security.jwt;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

@Getter
public class QuizAuthenticationToken extends JwtAuthenticationToken {

    private final UUID userId;

    public QuizAuthenticationToken(Jwt jwt, UUID userId) {
        super(jwt);
        this.userId = userId;
    }

    public QuizAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities, UUID userId) {
        super(jwt, authorities);
        this.userId = userId;
    }

    public QuizAuthenticationToken(Jwt jwt,
                                   Collection<? extends GrantedAuthority> authorities,
                                   UUID userId, String name) {
        super(jwt, authorities, name);
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        QuizAuthenticationToken that = (QuizAuthenticationToken) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId);
    }

}
