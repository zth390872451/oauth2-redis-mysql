package com.company.filter;

import com.company.utils.ApplicationSupport;
import com.company.utils.Oauth2Utils;
import com.company.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Oauth2Interceptor implements HandlerInterceptor {


	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		String accessToken = request.getParameter("access_token");
		RemoteTokenServices remoteTokenServices = (RemoteTokenServices) ApplicationSupport.getBean("remoteTokenServices");
		remoteTokenServices.setClientId("client_auth_mode");
//		remoteTokenServices.setClientSecret("k2appabc7893d34");

//		OAuth2AccessToken oAuth2AccessToken = remoteTokenServices.readAccessToken(accessToken);
		OAuth2Authentication oAuth2Authentication = remoteTokenServices.loadAuthentication(accessToken);
		OAuth2AccessToken oauth2AccessToken = Oauth2Utils.checkTokenInOauth2Client(accessToken);
		if (oauth2AccessToken==null){//非法的Token值
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			ResponseUtils.responseData(response,"非法的Token!");
			return false;
		}else if (oauth2AccessToken.isExpired()){//token失效
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			ResponseUtils.responseData(response,"Token失效，请重新登录!");
			return false;
		}
		 return true;
	}

	private boolean HandlerMethod(Object handler, Long memberId, Long holderId, Long currentMemberId, HttpServletRequest request){
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
						   HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
								HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}


}
