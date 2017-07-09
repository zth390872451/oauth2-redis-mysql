package com.company.filter;

import com.company.utils.Oauth2Utils;
import com.company.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对AccessToken进行检测，当出现AccessToken失效或者非法时，将直接返回401，未授权错误
 */
public class Oauth2Interceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		String accessToken = request.getParameter("access_token");
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

}
