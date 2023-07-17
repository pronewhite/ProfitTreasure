package com.hitsz.front.controller;

import com.Hitsz.api.model.RechargeRecord;
import com.hitsz.front.view.RechargeResult;
import com.hitsz.front.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@Api(tags = "充值业务")
@RestController
@RequestMapping("/v1/recharge")
public class RechargeController extends BaseInfoController{


    @ApiOperation("查询用户充值记录")
    @GetMapping("/details")
    public RespResult userRechargeInfo(@RequestHeader Integer uid,
                                       @RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                       @RequestParam(required = false,defaultValue = "6") Integer cows){
        RespResult respResult = RespResult.fail();
        if(uid != null && uid > 0){
            List<RechargeRecord> rechargeRecords = rechargeService.queryRechargeInfoByUid(uid, pageNo, cows);
            respResult = RespResult.ok();
            respResult.setList(changeType(rechargeRecords));
        }
        return respResult;
    }
    private List<RechargeResult> changeType(List<RechargeRecord> src){
        List<RechargeResult> results = new ArrayList<>();
        src.forEach(record -> {
            results.add(new RechargeResult(record));
        });
        return results;
    }

}
