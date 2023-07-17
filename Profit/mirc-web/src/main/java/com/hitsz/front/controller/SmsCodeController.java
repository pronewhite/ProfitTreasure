package com.hitsz.front.controller;

import com.Hitsz.common.Redis.RedisKey;
import com.Hitsz.common.enums.RCode;
import com.Hitsz.common.utils.CommonsUtils;
import com.hitsz.front.service.SmsService;
import com.hitsz.front.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "短信业务")
@RestController
@RequestMapping("v1/user")
public class SmsCodeController extends BaseInfoController{

    @Resource(name = "smaCodeRegisterImpl")
    private SmsService smsRegisterService;

    @Resource(name = "smaCodeLoginImpl")
    private SmsService smaLoginService;

    @ApiOperation("发送注册短信")
    @GetMapping("/code/register")
    public RespResult SmsRegister(String phone){
        RespResult respResult = RespResult.fail();
        if(CommonsUtils.judgePhoneIsCorrect(phone)){
            String smCode = RedisKey.KEY_SMS_CODE_REG + phone;
            //查看验证码是否有效
            if(stringRedisTemplate.hasKey(smCode)){
                //验证码已经发过
                respResult = RespResult.ok();
                respResult.setRCode(RCode.SMS_CODE_CAN_USE);
            }else if(smsRegisterService.sendSms(phone)){
                //验证码发送成功
                respResult = RespResult.ok();
            }
        }else{
            respResult.setRCode(RCode.PHONE_PATTERN_ERROR);
        }
        return respResult;
    }

    @ApiOperation("发送登录短信")
    @GetMapping("/code/login")
    public RespResult SmsLogin(String phone){
        RespResult respResult = RespResult.fail();
        if(CommonsUtils.judgePhoneIsCorrect(phone)){
            String smCode = RedisKey.KEY_SMS_CODE_LOGIN + phone;
            //查看验证码是否有效
            if(stringRedisTemplate.hasKey(smCode)){
                respResult = RespResult.ok();
                respResult.setRCode(RCode.SMS_CODE_CAN_USE);
            }else if(smaLoginService.sendSms(phone)){
                //验证码发送成功
                respResult = RespResult.ok();
            }
        }else{
            respResult.setRCode(RCode.PHONE_PATTERN_ERROR);
        }
        return respResult;
    }
}
