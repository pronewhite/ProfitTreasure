package com.hitsz.front.controller;

import com.Hitsz.api.pojo.BaseInfo;
import com.Hitsz.common.settings.StandardConstant;
import com.hitsz.front.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "平台信息功能")
@RestController
@RequestMapping("/v1")
public class PlatBaseInfoController extends BaseInfoController{

    @ApiOperation(value = "平台三项基本信息",notes = "年利率，注册人数，成交总金额")
    @GetMapping("/plat/info")
    public RespResult queryBaseInfo(){
        BaseInfo baseInfo = platBaseInfoService.queryPlatBaseInfo();
        RespResult respResult = RespResult.ok();
        respResult.setData(baseInfo);
        return respResult;
    }
}
