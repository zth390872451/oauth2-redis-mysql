package com.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 */
@RestController
@RequestMapping("/api")
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "success";
    }

}
