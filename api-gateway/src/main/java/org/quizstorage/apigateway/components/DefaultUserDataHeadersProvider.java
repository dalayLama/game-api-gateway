package org.quizstorage.apigateway.components;

import lombok.RequiredArgsConstructor;
import org.quizstorage.apigateway.conf.properties.UserDataHeadersProperties;
import org.quizstorage.apigateway.security.jwt.QuizAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultUserDataHeadersProvider implements UserDataHeadersProvider {

    private final UserDataHeadersProperties headersProperties;

    @Override
    public HttpHeaders getHttpHeaders(QuizAuthenticationToken authenticationToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(headersProperties.getIdHeaderName(), authenticationToken.getUserId().toString());
        httpHeaders.add(headersProperties.getNameHeaderName(), authenticationToken.getName());
        httpHeaders.addAll(headersProperties.getRolesHeaderName(), extractRoles(authenticationToken.getAuthorities()));
        return HttpHeaders.readOnlyHttpHeaders(httpHeaders);
    }

    private List<String> extractRoles(Collection<GrantedAuthority> authorities) {
        return authorities.stream().map(GrantedAuthority::getAuthority).toList();
    }

}
