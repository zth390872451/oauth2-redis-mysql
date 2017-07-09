package com.company.controller;

import com.company.utils.Oauth2Utils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class TokenController {

    /**
     * 覆盖了 spring-security-oauth2 内部的 endpoint oauth2/check_token
     * spring-security-oauth2 内部原有的该控制器 CheckTokenEndpoint，返回值，不符合自身业务要求，故覆盖之。
     */
    @GetMapping("/check_token")
    public OAuth2AccessToken getToken(@RequestParam(value = "token") String token){
        OAuth2AccessToken oAuth2AccessToken = Oauth2Utils.checkTokenInOauth2Server(token);
        return oAuth2AccessToken;
    }

    /**
     * 获取当前token对应的用户主体的凭证信息(认证对象)
     */
    @GetMapping("/getAuth")
    public OAuth2Authentication getAuth(@RequestParam(value = "token") String token){
        OAuth2Authentication oAuth2Authentication = Oauth2Utils.getAuthenticationInOauth2Server(token);
        return oAuth2Authentication;
    }

}
