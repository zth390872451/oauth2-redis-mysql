package com.company.filter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Oauth2Interceptor implements HandlerInterceptor {

	/*private static RedisService redisService = (RedisService) ApplicationSupport.getBean("redisServiceImpl");
	private static DruidDataSource dataSource = (DruidDataSource) ApplicationSupport.getBean("druidDataSource");
	private static MonitorRepository monitorRepository = (MonitorRepository) ApplicationSupport.getBean("monitorRepository");
	private static BarrierRepository barrierRepository = (BarrierRepository) ApplicationSupport.getBean("barrierRepository");
	private static FamilyNumberRepository familyNumberRepository = (FamilyNumberRepository) ApplicationSupport.getBean("familyNumberRepository");
	private static LogMessageRepository logMessageRepository = (LogMessageRepository)ApplicationSupport.getBean("logMessageRepository");
	private static HolderRepository holderRepository = (HolderRepository) ApplicationSupport.getBean("holderRepository");
	private static MemberRepository memberRepository = (MemberRepository) ApplicationSupport.getBean("memberRepository");*/


	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response, Object handler) throws Exception {
		/*if (request.getParameter("access_token")==null)
			return false;*/

		 return true;
	}

	private boolean HandlerMethod(Object handler, Long memberId, Long holderId, Long currentMemberId, HttpServletRequest request){
		System.out.println("HandlerMethod 被 invoked! " + handler);
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

	protected static String extractTokenKey(String value) {
		if (value == null) {
			return null;
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(value.getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}
}
