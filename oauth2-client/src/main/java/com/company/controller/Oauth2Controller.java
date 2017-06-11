package com.company.controller;


import com.company.utils.ApplicationSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * @Desc 认证登录接口
 *
 */
@RestController
@RequestMapping("/api/oauth2")
public class Oauth2Controller {
	
	private static final Logger log = LoggerFactory.getLogger(Oauth2Controller.class);


	/**
	 * //账号密码验证(密Oauth2的码模式)
	 * 获取token
	 */
	@RequestMapping(value = "/pwdToken",method = RequestMethod.POST)
	public Object accessToken(@RequestParam(value = "client_id") String client_id,
							  @RequestParam(value = "client_secret") String client_secret,
							  @RequestParam(value = "grant_type") String grant_type,
							  @RequestParam(value = "username") String username,
							  @RequestParam(value = "password") String password
									 ){
		//App端dm5加密后不足32位加零补齐的情况
		String pre = "";
		if (password.length() < 32) {
			int len = 32 - password.length();
			pre = String.format("%0" + len + "d", 0);
		}
		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
		details.setAccessTokenUri(ApplicationSupport.getParamVal("oauth.token"));
		details.setScope(Arrays.asList("read", "write"));
//		details.setClientSecret("password");
		details.setClientId(client_id);
		details.setClientSecret(client_secret);
		details.setUsername(username);
		details.setPassword(pre + password);

		ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
		OAuth2AccessToken accessToken = null;
		try {
			accessToken = provider.obtainAccessToken(details, new DefaultAccessTokenRequest());
		} catch (NullPointerException e) {
			log.error("授权失败原因：{}", e.getMessage());
			return "用户不存在";
		}catch (Exception e){
			log.error("授权失败原因：{}", e.getMessage());
			return "创建token失败";
		}



		String token = accessToken.getValue();
		int expiresIn = accessToken.getExpiresIn();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("access_token", token);
		map.put("token_type", accessToken.getTokenType());
		map.put("expires_in", expiresIn);

		return accessToken;
	}

	/**
	 * 客户端验证(Oauth2的客户端模式)
	 * 客户端获取token
	 */
	@RequestMapping(value = "/clientToken",method = RequestMethod.POST)
	public Object getToken(@RequestParam(value = "client_id") String client_id,
								   @RequestParam(value = "client_secret") String client_secret,
								   @RequestParam(value = "grant_type") String grant_type
								   ){

		ClientCredentialsResourceDetails clientCredentials = new ClientCredentialsResourceDetails();
		clientCredentials.setAccessTokenUri(ApplicationSupport.getParamVal("oauth.token"));
		clientCredentials.setScope(Arrays.asList("read", "write"));
//		clientCredentials.setClientSecret("client_secret");
		clientCredentials.setClientId(client_id);
		clientCredentials.setClientSecret(client_secret);
		clientCredentials.setGrantType(grant_type);
		ClientCredentialsAccessTokenProvider provider = new ClientCredentialsAccessTokenProvider();
		OAuth2AccessToken accessToken = null;
		try {
			accessToken = provider.obtainAccessToken(clientCredentials, new DefaultAccessTokenRequest());
		} catch (Exception e) {
			e.printStackTrace();
			return "error 401";
		}
		return accessToken+"";
	}

}
