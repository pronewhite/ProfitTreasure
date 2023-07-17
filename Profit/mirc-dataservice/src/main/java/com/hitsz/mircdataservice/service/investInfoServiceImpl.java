package com.hitsz.mircdataservice.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.Hitsz.api.model.BidInfo;
import com.Hitsz.api.model.FinanceAccount;
import com.Hitsz.api.model.ProductInfo;
import com.Hitsz.api.pojo.InvestInfo;
import com.Hitsz.api.service.InvestInfoService;
import com.Hitsz.common.settings.StandardConstant;
import com.Hitsz.common.utils.CommonsUtils;
import com.hitsz.mircdataservice.mapper.BidInfoMapper;
import com.hitsz.mircdataservice.mapper.FinanceAccountMapper;
import com.hitsz.mircdataservice.mapper.ProductInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = InvestInfoService.class,version = "1.0")
public class investInfoServiceImpl implements InvestInfoService {

    @Resource
    private FinanceAccountMapper accountMapper;
    @Resource
    private ProductInfoMapper productInfoMapper;

    @Resource
    private BidInfoMapper bidInfoMapper;
    @Override
    public List<InvestInfo> queryInvestInfoByProductId(Integer productId, Integer start, Integer cows) {

        List<InvestInfo> investInfos = new ArrayList<>();

        int offSet = (start - 1) * cows;
        investInfos = bidInfoMapper.selectInvestInfoByProductId(productId,offSet,cows);
        return investInfos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int investOperation(Integer uid, Integer proId, BigDecimal money) {
        int investStatus = 0;
        int rows = 0;
        FinanceAccount account = accountMapper.selectUserFiance(uid);
        if(account != null){
            if(CommonsUtils.CompareNums(account.getAvailableMoney(),money)){
                ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(proId);
                if(productInfo != null && productInfo.getProductStatus() == StandardConstant.PRODUCT_STATUS_CANINVEST){
                    //
                    if(CommonsUtils.CompareNums(productInfo.getLeftProductMoney(),money)
                            && CommonsUtils.CompareNums(money,productInfo.getBidMinLimit())
                            && CommonsUtils.CompareNums(productInfo.getBidMaxLimit(),money)){
                        //购买产品，更新账户信息
                        rows = accountMapper.updateUserFinanceInfo(uid,money);
                        if(rows < 1){
                            throw new RuntimeException("更新账户信息失败");
                        }

                        rows = productInfoMapper.updateProductMoney(proId,money);
                        //创建投资记录
                        //6.创建投资记录
                        BidInfo bidInfo = new BidInfo();
                        bidInfo.setBidMoney(money);
                        bidInfo.setBidStatus(StandardConstant.RETURN_STATUS_SUCCESS);
                        bidInfo.setBidTime(new Date());
                        bidInfo.setProdId(proId);
                        bidInfo.setUid(uid);
                        bidInfoMapper.insertSelective(bidInfo);

                        //更新产品状态，首先判断是否卖完
                        ProductInfo productInfo1 = productInfoMapper.selectByPrimaryKey(proId);
                        if(productInfo1.getLeftProductMoney().compareTo(new BigDecimal("0")) == 0){
                            rows = productInfoMapper.updateProductInfo(proId);
                            if(rows < 1){
                                throw new RuntimeException("更新投资产品状态失败");
                            }
                        }
                        //投资成功
                        investStatus = 1;
                    }else{
                        //不满足投标要求
                        investStatus = 2;
                    }
                }else{
                    //产品已满标
                    investStatus = 3;
                }
            }else{
                //用户余额不足
                investStatus = 4;
            }
        }else {
            //用户不存在
            investStatus = 5;
        }
        return investStatus;
    }
}
