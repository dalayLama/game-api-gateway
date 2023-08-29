package org.quizstorage.apigateway.gateway.filters;

import org.quizstorage.apigateway.security.SecurityFacade;
import org.quizstorage.apigateway.components.UserDataHeadersProvider;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class UserDataHeadersGatewayFilterFactory extends
        AbstractGatewayFilterFactory<UserDataHeadersGatewayFilterFactory.Config> {

    private final SecurityFacade securityFacade;

    private final UserDataHeadersProvider headersProvider;

    public UserDataHeadersGatewayFilterFactory(SecurityFacade securityFacade,
                                               UserDataHeadersProvider headersProvider) {
        super(UserDataHeadersGatewayFilterFactory.Config.class);
        this.securityFacade = securityFacade;
        this.headersProvider = headersProvider;
    }

    @Override
    public GatewayFilter apply(UserDataHeadersGatewayFilterFactory.Config config) {
        return (exchange, chain) -> securityFacade.getAuthenticatedToken()
                .map(headersProvider::getHttpHeaders)
                .map(httpHeaders -> {
                    exchange.getRequest().mutate().headers(headers -> headers.addAll(httpHeaders));
                    return exchange;
                })
                .flatMap(e -> {
                    return chain.filter(exchange);
                });
    }

    public static class Config {}

}
