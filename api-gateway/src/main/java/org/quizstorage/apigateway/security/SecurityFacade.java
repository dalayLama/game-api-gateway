package org.quizstorage.apigateway.security;

import org.quizstorage.apigateway.security.jwt.QuizAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SecurityFacade {

    public Mono<QuizAuthenticationToken> getAuthenticatedToken() {
        return ReactiveSecurityContextHolder.getContext()
                .map(context -> (QuizAuthenticationToken) context.getAuthentication());

    }

}
