package com.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 后续开发：提供接口，设置 client_id 和 client_secret 的相关接口
 */
@RestController
@RequestMapping("/oauth2/clientDetails")
public class OauthClientDetailsController {

    /* @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JdbcClientDetailsService jdbcClientDetailsService;


    @RequestMapping("/add")
    public BaseClientDetails addOauthClientDetails(String clientId,String resourceId,String scopes,String grantTypes,
                                        String authorities,String redirectUris,String secret){
        String encodeSecret = passwordEncoder.encode(secret);
        BaseClientDetails clientDetails = new BaseClientDetails(clientId,resourceId,scopes,grantTypes,authorities,redirectUris);
        clientDetails.setClientSecret(encodeSecret);
        jdbcClientDetailsService.addClientDetails(clientDetails);
        return clientDetails;
    }*/
}
