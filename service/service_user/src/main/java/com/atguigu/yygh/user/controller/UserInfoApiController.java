package com.atguigu.yygh.user.controller;

import com.atguigu.yygh.common.exception.YyghException;
import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.user.service.UserInfoService;
import com.atguigu.yygh.vo.user.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
@Api(tags = "用户登录相关 UserInfoApiController")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("用户手机号登录接口")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo) {
        try {
            Map<String, Object> map = userInfoService.loginUser(loginVo);
            return Result.ok(map);
        } catch (YyghException e){
            return Result.build(e.getCode(),e.getMessage());
        }
    }
}
