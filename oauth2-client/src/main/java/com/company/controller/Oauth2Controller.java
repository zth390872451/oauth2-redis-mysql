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


/**
 * @Desc 认证登录接口(获取AccessToken)
 */
@RestController
@RequestMapping("/api/oauth2")
public class Oauth2Controller {

	private static final Logger log = LoggerFactory.getLogger(Oauth2Controller.class);

	/**
	 * OAuth2的密码授权模式
	 */
	@RequestMapping(value = "/passwordMode",method = RequestMethod.POST)
	public Object accessToken(@RequestParam(value = "client_id") String client_id,
							  @RequestParam(value = "client_secret") String client_secret,
							  @RequestParam(value = "grant_type") String grant_type,
							  @RequestParam(value = "username") String username,
							  @RequestParam(value = "password") String password
									 ){
		//补足：对dm5加密后的密码不足32位加零补齐
		String fill = "";
		if (password.length() < 32) {//下面的details的password的长度必须32位，所以非32位则，需要补足位数
			int len = 32 - password.length();
			fill = String.format("%0" + len + "d", 0);
		}
		//创建一个包含需要请求的资源实体以及认证信息集合的对象
		ResourceOwnerPasswordResourceDetails details = new ResourceOwnerPasswordResourceDetails();
		//设置请求认证授权的服务器的地址
		details.setAccessTokenUri(ApplicationSupport.getParamVal("oauth.token"));
		//下面都是认证信息：所拥有的权限，认证的客户端，具体的用户
		details.setScope(Arrays.asList("read", "write"));
		details.setClientId(client_id);
		details.setClientSecret(client_secret);
		details.setUsername(username);
		details.setPassword(fill + password);

		ResourceOwnerPasswordAccessTokenProvider provider = new ResourceOwnerPasswordAccessTokenProvider();
		OAuth2AccessToken accessToken = null;
		try {
			//获取AccessToken
			// 1、(内部流程简介：根据上述信息，将构造一个前文一中的请求头为 "Basic Base64(username:password)" 的http请求
			//2、之后将向认证授权服务器的 oauth/oauth_token 端点发送请求，试图获取AccessToken
			accessToken = provider.obtainAccessToken(details, new DefaultAccessTokenRequest());
		} catch (NullPointerException e) {
			log.error("授权失败原因：{}", e.getMessage());
			return "用户不存在";
		}catch (Exception e){
			log.error("授权失败原因：{}", e.getMessage());
			return "创建token失败";
		}
		return accessToken;
	}

	/**
	 * Oauth2的受信任的客户端授权模式
	 */
	@RequestMapping(value = "/clientMode",method = RequestMethod.POST)
	public Object getToken(@RequestParam(value = "client_id") String client_id,
								   @RequestParam(value = "client_secret") String client_secret,
								   @RequestParam(value = "grant_type") String grant_type
								   ){
		//创建一个包含需要请求的资源实体以及认证信息集合的对象
		ClientCredentialsResourceDetails clientCredentials = new ClientCredentialsResourceDetails();
		clientCredentials.setAccessTokenUri(ApplicationSupport.getParamVal("oauth.token"));
		//下面都是认证信息：所拥有的权限，认证的客户端
		clientCredentials.setScope(Arrays.asList("read", "write"));
		clientCredentials.setClientId(client_id);
		clientCredentials.setClientSecret(client_secret);
		clientCredentials.setGrantType(grant_type);
		ClientCredentialsAccessTokenProvider provider = new ClientCredentialsAccessTokenProvider();
		OAuth2AccessToken accessToken = null;
		try {
			accessToken = provider.obtainAccessToken(clientCredentials, new DefaultAccessTokenRequest());
		} catch (Exception e) {
			e.printStackTrace();
			return "获取AccessToken失败";
		}
		return accessToken;
	}

}
