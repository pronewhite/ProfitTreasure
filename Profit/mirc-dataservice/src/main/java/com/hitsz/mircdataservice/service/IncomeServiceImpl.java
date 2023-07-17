package com.hitsz.mircdataservice.service;

import com.Hitsz.api.model.BidInfo;
import com.Hitsz.api.model.IncomeRecord;
import com.Hitsz.api.model.ProductInfo;
import com.Hitsz.api.service.IncomeService;
import com.Hitsz.common.settings.ProductTypeConstant;
import com.Hitsz.common.settings.StandardConstant;
import com.hitsz.mircdataservice.mapper.BidInfoMapper;
import com.hitsz.mircdataservice.mapper.FinanceAccountMapper;
import com.hitsz.mircdataservice.mapper.IncomeRecordMapper;
import com.hitsz.mircdataservice.mapper.ProductInfoMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@DubboService(interfaceClass = IncomeService.class,version = "1.0")
public class IncomeServiceImpl implements IncomeService {

    @Resource
    private FinanceAccountMapper financeAccountMapper;
    @Resource
    private IncomeRecordMapper incomeRecordMapper;
    @Resource
    private BidInfoMapper bidInfoMapper;

    @Resource
    private ProductInfoMapper productInfoMapper;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized void geneteIncomePlan() {

        //要处理的产品理财记录
        Date curDate = new Date();
        Date begin = DateUtils.truncate(DateUtils.addDays(curDate,-1), Calendar.DATE);
        Date end = DateUtils.truncate(curDate, Calendar.DATE);

        //查询产品理财记录
        List<ProductInfo> productInfoList = productInfoMapper.selectFulledTimeProduct(begin,end);

        //查询每个理财产品的投资记录
        int rows = 0;

        BigDecimal dayRate = null;
        BigDecimal cycle = null;
        BigDecimal income = null;
        Date incomeDate = null;


        for(ProductInfo product: productInfoList){

            //日利率
            dayRate = product.getRate().divide(new BigDecimal("360"),10, RoundingMode.HALF_UP)
                    .divide(new BigDecimal("100"),10,RoundingMode.HALF_UP);
            //产品周期
            if(product.getProductType() == ProductTypeConstant.PRODUCT_TYPE_XINSHOUBAO){
                cycle = new BigDecimal(product.getCycle());
                incomeDate = DateUtils.addDays(product.getProductFullTime(),(1+product.getCycle()));
            }else{
                cycle = new BigDecimal(product.getCycle() * 30);
                incomeDate = DateUtils.addDays(product.getProductFullTime(),(1+product.getCycle() * 30));
            }
            List<BidInfo> bidInfoList = bidInfoMapper.selectProductByProId(product.getId());
            for(BidInfo bidInfo:bidInfoList){
                income = bidInfo.getBidMoney().multiply(dayRate).multiply(cycle);

                IncomeRecord incomeRecord = new IncomeRecord();
                incomeRecord.setIncomeDate(incomeDate);
                incomeRecord.setIncomeMoney(income);
                incomeRecord.setBidMoney(bidInfo.getBidMoney());
                incomeRecord.setBidId(bidInfo.getId());
                incomeRecord.setProdId(bidInfo.getProdId());
                incomeRecord.setIncomeStatus(StandardConstant.INCOME_STATUS_PLAN);
                incomeRecord.setUid(bidInfo.getUid());
                incomeRecordMapper.insertSelective(incomeRecord);
            }

            //更新产品的状态
            rows = productInfoMapper.updateProductStatus(product.getId(),StandardConstant.PRODUCT_STATUS_MANBIAO_USERD);
            if( rows < 1 ){
                throw new RuntimeException("更新产品转台失败");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized void ReturnIncome() {

        Date curDate = new Date();
        //找出收益返还时间
        Date yesDate = DateUtils.truncate(DateUtils.addDays(curDate,-1),Calendar.DATE);

        //查询出所有收益到期的产品
        List<IncomeRecord> incomeRecordList = incomeRecordMapper.selectExipredProduct(yesDate);

        int rows = 0;
        for(IncomeRecord incomeRecord : incomeRecordList){

            rows = financeAccountMapper.updateAccountToAddProfit(incomeRecord.getUid(),
                                                                 incomeRecord.getBidMoney(),
                                                                 incomeRecord.getIncomeMoney());

            if(rows < 1){
                throw new RuntimeException("收益返还失败");
            }
            //更新收益状态
            incomeRecord.setIncomeStatus(StandardConstant.INCOME_STATUS_BACK);
            rows = incomeRecordMapper.updateByPrimaryKeySelective(incomeRecord);
            if(rows < 1){
                throw new RuntimeException("收益返还失败");
            }
        }
    }
}
