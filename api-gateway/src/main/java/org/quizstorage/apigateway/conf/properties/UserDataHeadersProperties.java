package org.quizstorage.apigateway.conf.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "user-data.headers")
@Getter
@Setter
public class UserDataHeadersProperties {

    private String idHeaderName;

    private String nameHeaderName;

    private String rolesHeaderName;

}
