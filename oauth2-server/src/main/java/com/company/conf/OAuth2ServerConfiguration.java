package com.company.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer//于配置 OAuth 2.0 授权服务器机制
public class OAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {


	@Autowired
	DataSource dataSource;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	/*******************************认证流程服务相关配置【BasicAuthenticationFilter类中涉及的Bean的相关配置】***********************************/

	/**
	 *  配置：安全检查流程
	 *  默认过滤器：BasicAuthenticationFilter
	 *  1、oauth_client_details表中clientSecret字段加密【ClientDetails属性secret】
	 *  2、CheckEndpoint类的接口 oauth/check_token 无需经过过滤器过滤，默认值：denyAll()
	 * @param security
	 * @throws Exception
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
		security.passwordEncoder(new BCryptPasswordEncoder());
		security.checkTokenAccess("permitAll()");
	}

	/**
	 * 认证服务检查 ClientDetailsService.loadClientByClientId
	 * 数据来源:1、内存 2、数据库
	 * @return
	 */
	@Bean
	public ClientDetailsService clientDetailsService(){
		ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
		return clientDetailsService;
	}

	/**
	 * 配置 oauth_client_details【client_id和client_secret等】信息的认证【检查ClientDetails的合法性】服务
	 * @param clients
	 * @throws Exception
     */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(clientDetailsService());
	}

	/**
	 * 用户信息认证检查类UserDetailsService的配置，该类用以检查 UserDetails的合法性，
	 * 此处注入的是自定义实现类： CustomUserDetailsService
	 * 框架提供的具体实现有：
	 * 	JdbcDaoImpl、CachingUserDetailsService、JdbcUserDetailsManager等
     */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore()).userDetailsService(userDetailsService);
	}


	/*******************************授权流程服务相关配置【TokenEndpoint类中/oauth/token接口涉及的Bean的相关配置】***********************************/

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	/**
	 * 配置AccessToken的存储方式：此处使用Redis存储
	 * Token的可选存储方式
	 * 1、InMemoryTokenStore
	 * 2、JdbcTokenStore
	 * 3、JwtTokenStore
	 * 4、RedisTokenStore
	 * 5、JwkTokenStore
	 */
	@Bean
	public TokenStore tokenStore() {
		return new RedisTokenStore(redisConnectionFactory);
	}

}
