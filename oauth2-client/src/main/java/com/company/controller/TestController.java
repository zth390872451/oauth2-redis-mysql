package com.company.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/6/14 0014.
 */
@RestController
public class TestController {
    @RequestMapping("/test")
    public String test(){
        return "success";
    }

}
