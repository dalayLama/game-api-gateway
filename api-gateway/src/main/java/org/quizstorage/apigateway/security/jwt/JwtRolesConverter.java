package org.quizstorage.apigateway.security.jwt;

import lombok.RequiredArgsConstructor;
import org.quizstorage.apigateway.conf.properties.JwtAuthConverterProperties;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public final class JwtRolesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private static final String RESOURCE_ACCESS_NAME = "resource_access";

    private static final String ROLES_KEY_NAME = "roles";

    private final JwtAuthConverterProperties converterProperties;

    @Override
    public Collection<GrantedAuthority> convert(@NonNull Jwt jwt) {
        return extractRoles(jwt)
                .map(this::generateRole)
                .collect(Collectors.toSet());
    }

    private Stream<String> extractRoles(Jwt jwt) {
        return Optional.ofNullable(jwt.getClaimAsMap(RESOURCE_ACCESS_NAME))
                .map(resourceAccess -> (Map<String, Object>) resourceAccess.get(converterProperties.getResourceId()))
                .map(resource -> (Collection<String>) resource.get(ROLES_KEY_NAME))
                .stream().flatMap(Collection::stream);
    }

    private GrantedAuthority generateRole(String role) {
        String rolePrefix = converterProperties.getRolePrefix().toUpperCase();
        String roleWithPrefix = "%s%s".formatted(rolePrefix, role.toUpperCase());
        return new SimpleGrantedAuthority(roleWithPrefix);
    }

}
