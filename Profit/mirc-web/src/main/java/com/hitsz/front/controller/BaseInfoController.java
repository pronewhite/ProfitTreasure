package com.hitsz.front.controller;

import com.Hitsz.api.service.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

/**
 * 所有controller的父类，可以在这里定义相同的属性和方法
 */
public class BaseInfoController {

    @Resource
    protected StringRedisTemplate stringRedisTemplate;
    @DubboReference(interfaceClass = PlatBaseInfoService.class,version = "1.0")
    protected PlatBaseInfoService platBaseInfoService;
    @DubboReference(interfaceClass = ProductInfoService.class,version = "1.0")
    protected ProductInfoService productInfoService;

    @DubboReference(interfaceClass = InvestInfoService.class,version = "1.0")
    protected InvestInfoService investInfoService;

    @DubboReference(interfaceClass = UserRegisterService.class,version = "1.0")
    protected UserRegisterService userRegisterService;

    @DubboReference(interfaceClass = RechargeService.class,version = "1.0")
    protected RechargeService rechargeService;
}
