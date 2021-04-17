package com.atguigu.yygh.msm.controller;

import com.atguigu.yygh.common.result.Result;
import com.atguigu.yygh.msm.service.MsmService;
import com.atguigu.yygh.msm.utils.RandomUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/msm")
@Api(tags = "发送短信控制器 MsmApiController")
public class MsmApiController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送手机短信验证码
    @GetMapping("send/{phone}")
    public Result sendCode(@PathVariable String phone) {
        //从redis里获取验证码，如果获取得到，直接返回ok
        //redis中，key是手机号，值是验证码
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)) {
            return Result.ok();
        }
        //如果从redis中取不到
        //生成验证码，通过短信发送
        code = RandomUtils.getSixBitRandom();
        boolean isSend = msmService.send(phone,code);
        //生成的验证码放到redis中，设置有效时间
        if (isSend) {
           redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
           return Result.ok();
        } else {
            return Result.fail().message("发送短信失败");
        }
    }
}