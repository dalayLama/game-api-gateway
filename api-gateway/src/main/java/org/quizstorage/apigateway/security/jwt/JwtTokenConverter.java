package org.quizstorage.apigateway.security.jwt;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import reactor.core.publisher.Mono;

public interface JwtTokenConverter extends Converter<Jwt, Mono<AbstractAuthenticationToken>> {
}
