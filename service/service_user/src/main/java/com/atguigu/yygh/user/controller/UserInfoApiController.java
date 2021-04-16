package com.atguigu.yygh.user.controller;

import com.atguigu.yygh.user.service.UserInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户登录相关 UserInfoApiController")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;
}
