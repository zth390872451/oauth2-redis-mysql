package com.company.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableAutoConfiguration
@EnableOAuth2Client
public class OAuth2Configuration {

    @Value("${oauth.check_token}")
    private String checkTokenEndpointUrl;

    @Bean
    public RemoteTokenServices remoteTokenServices(){
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl(checkTokenEndpointUrl);
        return remoteTokenServices;
    }

}
