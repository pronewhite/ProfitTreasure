package com.hitsz.front.controller;

import com.Hitsz.api.model.User;
import com.Hitsz.api.pojo.UserInvestInfo;
import com.Hitsz.common.enums.RCode;
import com.Hitsz.common.utils.CommonsUtils;
import com.Hitsz.common.utils.JwtUtil;
import com.hitsz.front.service.RealnameServiceImpl;
import com.hitsz.front.service.SmsService;
import com.hitsz.front.view.RespResult;
import com.hitsz.front.vo.RealnameVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户功能")
@RestController
@RequestMapping("/v1/user")
public class UserController extends BaseInfoController{

    @Resource
    private RealnameServiceImpl realnameService;

    @Resource(name = "smaCodeRegisterImpl")
    private SmsService smaRegisterService;
    @Resource
    private JwtUtil jwtUtil;
    @Resource(name = "smaCodeLoginImpl")
    private SmsService smsService;
    @ApiOperation( value = "用户注册",notes="根据用户输入的手机号，密码以及验证码来实现用户注册")
    @PostMapping("/register")
    public RespResult userRegisterOperat(String phone,String psCode,String sCode){
        RespResult respResult = RespResult.fail();

        if(CommonsUtils.judgePhoneIsCorrect(phone)){
            if(psCode != null && psCode.length() == 32){
                //判断验证码是否正确
                if(smaRegisterService.checkPaCodeAndsCode(phone,sCode)){
                    int registerResult = userRegisterService.UserRegister(phone,psCode);
                    if(registerResult == 1){
                        respResult = RespResult.ok();
                    } else if (registerResult == 2) {

                        respResult.setRCode(RCode.PHONE_EXISTS);
                    }else {
                        respResult.setRCode(RCode.REQUEST_PARAM_ERR);
                    }
                }else{
                    respResult.setRCode(RCode.SMS_CODE_INVALID);
                }
            }else {
                respResult.setRCode(RCode.PASSWORD_PATTERN_ERROR);
            }
        }else {
            respResult.setRCode(RCode.PHONE_PATTERN_ERROR);
        }
        return respResult;
    }

    @ApiOperation(value = "判断用户是否注册",notes = "注册时，首先要判断用户手机号是否注册，才能进行后续步骤")
    @GetMapping("/phone/ifExist")
    public RespResult userIfCanRegister(String phone){

        RespResult respResult = new RespResult();
        respResult.setRCode(RCode.PHONE_EXISTS);
        if(CommonsUtils.judgePhoneIsCorrect(phone)){
            User user = userRegisterService.UserTryRegister(phone);
            if(user == null){
                respResult = RespResult.ok();
            }
        }else{
            respResult.setRCode(RCode.PHONE_PATTERN_ERROR);
        }
        return respResult;
    }

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public RespResult userLogin(String phone,String psCode,String sCode) throws Exception {
        RespResult respResult = RespResult.fail();

        if(CommonsUtils.judgePhoneIsCorrect(phone) && (psCode != null && psCode.length() == 32)){
            if(smsService.checkPaCodeAndsCode(phone,sCode)){
                //执行数据库查询
                User user = userRegisterService.userLogin(phone,psCode);
                if(user != null){
                    //登录成功
                    Map<String,Object> data = new HashMap<>();
                    data.put("userId",user.getId());
                    String jwt = jwtUtil.createJwt(data,120);

                    respResult = RespResult.ok();
                    respResult.setJwtToken(jwt);

                    Map<String,Object> userInfo = new HashMap<>();
                    userInfo.put("uid",user.getId());
                    userInfo.put("phone",user.getPhone());
                    userInfo.put("name",user.getName());

                    respResult.setData(userInfo);
                }else{
                    respResult.setRCode(RCode.PHONE_OR_PASSWORD_ERROR);
                }
            }else{//验证码无效
                respResult.setRCode(RCode.SMS_CODE_INVALID);
            }
        }else {//请求参数有误
            respResult.setRCode(RCode.REQUEST_PARAM_ERR);
        }
        return respResult;
    }

    @ApiOperation(value = ("实名认证"),notes = "根据用户手机号，姓名，身份证号实名认证")
    @PostMapping("/realname")
    public RespResult userRealnameOperation(@RequestBody RealnameVo realnameVo){
        RespResult respResult = RespResult.fail();
        respResult.setRCode(RCode.REQUEST_PARAM_ERR);
        if(CommonsUtils.judgePhoneIsCorrect(realnameVo.getPhone())){
            if(StringUtils.isNotBlank(realnameVo.getIdCard()) && StringUtils.isNotBlank(realnameVo.getName())){

                //判断用户是否已经认证过
                User user = userRegisterService.queryUserByphone(realnameVo.getPhone());
                if(user != null){
                    if(user.getName() != null){
                        //已经认证过，不需要在认证
                        respResult.setRCode(RCode.REALNAME_HAVEDONE);
                    }else{
                        //第一次认证，需要进行认证操作
                        //调用第三方接口
                        boolean realNameResult = realnameService.handleRealName(realnameVo.getPhone(),
                                realnameVo.getName(),
                                realnameVo.getIdCard());
                        if(realNameResult){
                            respResult = RespResult.ok();
                        }else{
                            respResult.setRCode(RCode.REALNAME_FAIL);
                        }
                    }
                }

            }
        }
        return respResult;
    }

    @ApiOperation("用户中心")
    @GetMapping("/center")
    public RespResult userInvestInfOperation(@RequestHeader("uid") Integer uid){
        RespResult respResult = RespResult.fail();
        if(uid != null && uid > 0){
            UserInvestInfo userInvestInfo = userRegisterService.queryUserInvestINfoByUid(uid);
            respResult = RespResult.ok();
            Map<String,Object> map = new HashMap<>();
            map.put("name",userInvestInfo.getName());
            map.put("phone",userInvestInfo.getPhone());
            map.put("money",userInvestInfo.getAvailableMoney());
            if(userInvestInfo.getLastLoginTime() != null){
                map.put("lastLoginTime", DateFormatUtils.format(userInvestInfo.getLastLoginTime(),
                                 "yyyy-MM-dd HH:mm:ss"));
            }else {
                map.put("lastLoginTime","----");
            }
            respResult.setData(map);
        }
        return respResult;
    }
}
