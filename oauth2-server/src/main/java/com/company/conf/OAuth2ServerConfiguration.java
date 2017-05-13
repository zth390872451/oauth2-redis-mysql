package com.company.conf;

import com.company.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import redis.clients.jedis.JedisPoolConfig;

import javax.sql.DataSource;
import java.security.AuthProvider;

@Configuration
@EnableAuthorizationServer//于配置 OAuth 2.0 授权服务器机制
public class OAuth2ServerConfiguration extends AuthorizationServerConfigurerAdapter {


	@Autowired
	DataSource dataSource;

	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//--------------------------------------------------------------------------------------------//
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailsService userDetailsService;


	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		/*clients.inMemory().withClient("wxb_doki_client").secret("k2appabc7893d34").scopes("read")
				.authorizedGrantTypes("client_credentials").authorizedGrantTypes("write")
				.autoApprove("read").autoApprove("write").authorities("USER")
				.and()
				.withClient("wxb_doki_api_pwd").secret("k2appabc7893d34").scopes("read")
				.authorizedGrantTypes("client_credentials").authorizedGrantTypes("write").authorities("USER")
				.autoAppove("read").autoApprove("write");*/
//		clients.inMemory();
		ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
		clients.withClientDetails(clientDetailsService).jdbc()/*.dataSource(dataSource).passwordEncoder(passwordEncoder)*/;
	}



	@Bean
	public TokenStore tokenStore() {
		return new RedisTokenStore(redisConnectionFactory());
	}

	/*@Bean
	public AuthenticationManager authenticationManager(){
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return (AuthenticationManager) daoAuthenticationProvider;
	}*/


	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)
			throws Exception {
		/*endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).userDetailsService(userDetailsService);*/
		endpoints/*.tokenStore(tokenStore())*/.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	}

	@Bean
	public RedisTokenStore redisTokenStoreBean() {
		return new RedisTokenStore(redisConnectionFactory());
	}

	@Bean
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setPort(6379);
		jedisConnectionFactory.setPassword("rediS100");
		jedisConnectionFactory.setDatabase(8);
		jedisConnectionFactory.setHostName("192.168.2.35");
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(8);
		jedisPoolConfig.setMinIdle(0);
		jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
		return jedisConnectionFactory;
	}


	@Bean
	public AuthorizationServerTokenServices defaultTokenServices() {
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setTokenStore(new RedisTokenStore(redisConnectionFactory()));//设置Token的存储方式
		return tokenServices;
	}

	/**
	 *  1.clientSecret字段加密
	 * @param security
	 * @throws Exception
     */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients();
		security.passwordEncoder(passwordEncoder);
		security.checkTokenAccess("permitAll()");//允许 check_token 端点无需校验
	}

}
