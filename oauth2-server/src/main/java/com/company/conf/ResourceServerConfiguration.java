package com.company.conf;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;


//@Configuration
//@EnableResourceServer
//保留
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		/*http
			.authorizeRequests()
				.antMatchers("/users").hasRole("ADMIN")
				.antMatchers("/greeting").authenticated();*/
		// @formatter:on
	}
}
