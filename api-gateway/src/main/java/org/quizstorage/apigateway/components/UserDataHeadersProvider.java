package org.quizstorage.apigateway.components;

import org.quizstorage.apigateway.security.jwt.QuizAuthenticationToken;
import org.springframework.http.HttpHeaders;

public interface UserDataHeadersProvider {

    HttpHeaders getHttpHeaders(QuizAuthenticationToken authenticationToken);

}
