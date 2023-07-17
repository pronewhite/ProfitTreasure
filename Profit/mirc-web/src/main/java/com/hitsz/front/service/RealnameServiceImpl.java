package com.hitsz.front.service;

import com.Hitsz.api.service.UserRegisterService;
import com.Hitsz.common.utils.HttpClientsUtil;
import com.alibaba.fastjson.JSONObject;
import com.hitsz.front.config.JdwRealNameConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class RealnameServiceImpl {

    @Resource
    private JdwRealNameConfig jdwxRealNameConfig;

    @Resource
    private UserRegisterService userService;


    /** 实名认证业务，true表示认证成功*/
    public boolean handleRealName(String phone,String name,String idCard){

        Map<String,String> params = new HashMap<>();

        boolean realName = false;
        //调用第三方接口需要使用的用户信息
        params.put("cardName",idCard);
        params.put("realName",name);
        params.put("appkey", jdwxRealNameConfig.getAppkey());

        try {
            //String resp = HttpClientsUtil.doGet(jdwxRealNameConfig.getUrl(),params);
            String resp="{\n" +
                    "    \"code\": \"10000\",\n" +
                    "    \"charge\": false,\n" +
                    "    \"remain\": 1305,\n" +
                    "    \"msg\": \"查询成功\",\n" +
                    "    \"result\": {\n" +
                    "        \"error_code\": 0,\n" +
                    "        \"reason\": \"成功\",\n" +
                    "        \"result\": {\n" +
                    "            \"realname\": \""+name+"\",\n" +
                    "            \"idcard\": \"350721197702134399\",\n" +
                    "            \"isok\": true\n" +
                    "        }\n" +
                    "    }\n" +
                    "}";
            if(StringUtils.isNotBlank(resp)){
                JSONObject jsonObject = JSONObject.parseObject(resp);
                if("10000".equalsIgnoreCase(jsonObject.getString("code"))){
                    realName  = jsonObject.getJSONObject("result").getJSONObject("result").getBoolean("isok");

                    //更新实名认证信息
                   boolean modifyResult = userService.modifyUser(phone,name,idCard);
                   realName = modifyResult;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return realName;
    }
}
