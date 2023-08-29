package org.quizstorage.apigateway.security.jwt;

import org.quizstorage.apigateway.conf.properties.JwtAuthConverterProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class JwtAuthConverter implements JwtTokenConverter {

    private final List<Converter<Jwt, Collection<GrantedAuthority>>> grantedAuthoritiesConverters;

    private final JwtAuthConverterProperties converterProperties;

    public JwtAuthConverter(List<Converter<Jwt, Collection<GrantedAuthority>>> grantedAuthoritiesConverters,
                            JwtAuthConverterProperties converterProperties) {
        this.grantedAuthoritiesConverters = grantedAuthoritiesConverters;
        this.converterProperties = converterProperties;
    }

    @Override
    public Mono<AbstractAuthenticationToken> convert(@NonNull Jwt jwt) {
        return Mono.fromCallable(() -> createAuthenticationToken(jwt));
    }

    private AbstractAuthenticationToken createAuthenticationToken(Jwt jwt) {
        List<GrantedAuthority> grantedAuthorities = grantedAuthoritiesConverters.stream()
                .map(converter -> converter.convert(jwt))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .toList();
        return new QuizAuthenticationToken(jwt, grantedAuthorities, extractUserId(jwt), extractUsername(jwt));
    }

    private UUID extractUserId(Jwt jwt) {
        return Optional.ofNullable(jwt.getClaimAsString(JwtClaimNames.SUB))
                .map(UUID::fromString)
                .orElse(null);
    }

    private String extractUsername(Jwt jwt) {
        return Optional.ofNullable(converterProperties.getUsernameAttribute())
                .filter(attribute -> !attribute.isBlank())
                .map(jwt::getClaimAsString)
                .orElse(null);
    }

}
