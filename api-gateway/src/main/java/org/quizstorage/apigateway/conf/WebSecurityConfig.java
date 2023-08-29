package org.quizstorage.apigateway.conf;

import lombok.RequiredArgsConstructor;
import org.quizstorage.apigateway.security.jwt.JwtTokenConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenConverter jwtConverter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(spec -> spec
                        .pathMatchers("/ws/**").permitAll()
                        .pathMatchers(HttpMethod.POST, "/api/v1/login/*", "/api/v1/sign-up/*").permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(spec -> spec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwtConverter))
                );
        return http.build();
    }

    @Bean
    public WebSocketHandlerAdapter adapter() {
        return new WebSocketHandlerAdapter();
    }

}
