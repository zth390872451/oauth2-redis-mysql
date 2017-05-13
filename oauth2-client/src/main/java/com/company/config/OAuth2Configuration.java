package com.company.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableAutoConfiguration
@EnableOAuth2Client
public class OAuth2Configuration {

	@Value("${oauth.token}")
	private String tokenUrl;

	@Bean
	protected ResourceOwnerPasswordResourceDetails details() {
		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
		details.setAccessTokenUri(tokenUrl);
		details.setScope(Arrays.asList("read", "write"));
		details.setClientSecret("password");
		return details;
	}
	
	@Bean
	protected ClientCredentialsResourceDetails clientCredentialsDetails() {
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		details.setAccessTokenUri(tokenUrl);
		details.setScope(Arrays.asList("read", "write"));
		details.setClientSecret("client_secret");
		return details;
	}
}
