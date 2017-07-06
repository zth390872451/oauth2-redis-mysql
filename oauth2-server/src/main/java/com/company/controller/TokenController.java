package com.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/5/9.
 */
@RestController
@RequestMapping("/oauth")
public class TokenController {

    /**
     * 覆盖了 spring-security-oauth2 内部的 endpoint oauth2/check_token
     * spring-security-oauth2 内部原有的该端点，返回值，不符合自身要求，故重写覆盖之。
     * @param token
     * @return
     */
    /*@PostMapping("/check_token")
    public OAuth2AccessToken check_token(@RequestParam(value = "token") String token){
        OAuth2AccessToken oAuth2AccessToken = Oauth2Utils.checkTokenInOauth2Server(token);
        return oAuth2AccessToken;
    }

    @PostMapping("/getAuth")
    public OAuth2Authentication getAuth(@RequestParam(value = "token") String token){
        OAuth2Authentication oAuth2Authentication = Oauth2Utils.getAuthenticationInOauth2Server(token);
        return oAuth2Authentication;
    }*/

}
