package com.hitsz.mircdataservice.service;

import com.Hitsz.api.pojo.BaseInfo;
import com.Hitsz.api.service.PlatBaseInfoService;
import com.hitsz.mircdataservice.mapper.BidInfoMapper;
import com.hitsz.mircdataservice.mapper.ProductInfoMapper;
import com.hitsz.mircdataservice.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.math.BigDecimal;

@DubboService(interfaceClass = PlatBaseInfoService.class,version = "1.0")
public class platBaseInfoServiceImpl implements PlatBaseInfoService {

    @Resource
    private BidInfoMapper bidInfoMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;
    @Resource
    private UserMapper userMapper;
    /**
     * 查询年利率，注册总人数，统计成交总金额
     * @return
     */
    @Override
    public BaseInfo queryPlatBaseInfo() {

        //年利率
        BigDecimal avgRate = productInfoMapper.selectAvgRate();
        //注册总人数
        int userCounts = userMapper.selectAllRegisterUsers();
        //成交总金额
        BigDecimal allsuccessedBidMoney = bidInfoMapper.selectAllsuccessedBidMoney();

        BaseInfo baseInfo = new BaseInfo(avgRate,userCounts,allsuccessedBidMoney);
        return baseInfo;
    }
}
