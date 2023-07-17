package com.hitsz.front.controller;

import com.Hitsz.api.model.User;
import com.Hitsz.common.Redis.RedisKey;
import com.Hitsz.common.utils.CommonsUtils;
import com.hitsz.front.view.RankView;
import com.hitsz.front.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Api(tags = "投资理财产品")
@RestController
@RequestMapping("/v1")
public class InvestController extends BaseInfoController{

    @ApiOperation(value = "投资排行榜",notes = "显示手机号，投资金额")
    @GetMapping("/invest/info")
    public RespResult GetInvestInfo(){
        RespResult result = RespResult.fail();
        //从redis查询数据
        Set<ZSetOperations.TypedTuple<String>> sets = stringRedisTemplate
                .boundZSetOps(RedisKey.KEY_INVEST_RANK).reverseRangeWithScores(0, 2);

        List<RankView> rankViews = new ArrayList<>();
        sets.forEach(tuple -> {
            rankViews.add(new RankView(CommonsUtils.makePhoneNotEasyTolook(tuple.getValue()),tuple.getScore()));
        });

        result = RespResult.ok();
        result.setList(rankViews);
        return result;
    }

    @ApiOperation("用户投资")
    @PostMapping("/invest/product")
    public RespResult userInvest(
                                  @RequestHeader Integer uid,
                                  @RequestParam Integer proId,
                                  @RequestParam BigDecimal money){
        RespResult respResult = RespResult.fail();
        if((uid != null && uid > 0) && (proId != null && proId > 0) && (money != null && money.intValue() % 100 == 0) ){
            int investResult = investInfoService.investOperation(uid, proId, money);
            switch (investResult){
                case 0:{
                    respResult.setMsg("投资参数有错误");
                    break;
                }
                case 1:{
                    respResult = RespResult.ok();
                    respResult.setMsg("投资成功");
                    //更新排行榜
                    modifyInvestRank(uid,money);
                    break;
                }
                case 2:{
                    respResult.setMsg("不满足投标要求");
                    break;
                }
                case 3:{
                    respResult.setMsg("产品已满标");
                    break;
                }
                case 4:{
                    respResult.setMsg("用户余额不足");
                    break;
                }
                case 5:{
                    respResult.setMsg("用户不存在");
                    break;
                }
            }
        }
        return respResult;
    }

    //更新排行榜
    private void modifyInvestRank(Integer uid,BigDecimal money){
        User user  = userRegisterService.queryByUid(uid);
        if(user != null){
            //更新redis中的投资排行榜
            String key = RedisKey.KEY_INVEST_RANK;
            stringRedisTemplate.boundZSetOps(key).incrementScore(
                    user.getPhone(),money.doubleValue());
        }
    }
}
