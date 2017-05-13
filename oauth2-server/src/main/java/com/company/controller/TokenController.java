package com.company.controller;

import com.company.utils.Oauth2Utils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GetMapping("/check_token")
    public OAuth2AccessToken check_token(@RequestParam(value = "token") String token){
        OAuth2AccessToken oAuth2AccessToken = Oauth2Utils.checkTokenInOauth2Server(token);
        return oAuth2AccessToken;
    }

}
