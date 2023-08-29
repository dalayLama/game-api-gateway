package org.quizstorage.apigateway.conf.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt.auth.converter")
@Getter
@Setter
public class JwtAuthConverterProperties {

    private String resourceId;

    private String usernameAttribute;

    private String rolePrefix;

}
