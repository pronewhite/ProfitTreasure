package com.hitsz.mircdataservice.service;

import com.Hitsz.api.model.RechargeRecord;
import com.Hitsz.api.service.RechargeService;
import com.Hitsz.api.service.UserRegisterService;
import com.Hitsz.common.settings.StandardConstant;
import com.hitsz.mircdataservice.mapper.FinanceAccountMapper;
import com.hitsz.mircdataservice.mapper.RechargeRecordMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@DubboService(interfaceClass = RechargeService.class,version = "1.0")
public class RechargeServiceImpl implements RechargeService {

    @Resource
    private FinanceAccountMapper accountMapper;
    @Resource
    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public List<RechargeRecord> queryRechargeInfoByUid(Integer uid, Integer pageNo, Integer cows) {
        List<RechargeRecord> records = null;
        int offSet = (pageNo - 1) * cows;
        return  rechargeRecordMapper.selectUserRechargeInfo(uid,offSet,cows);
    }

    @Override
    public int addRechargeRecord(RechargeRecord record) {
        return rechargeRecordMapper.insertSelective(record);
    }

    /*处理后续充值*/
    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized int handleKQNotify(String orderId, String payAmount, String payResult) {
        int result = 0;//订单不存在
        int rows =  0;
        //1.查询订单
        RechargeRecord record = rechargeRecordMapper.selectByRechargeNo(orderId);
        if(record != null ){
            if( record.getRechargeStatus() == StandardConstant.RECHARGE_STATUS_PROCESSING){
                //2.判断金额是否一致
                String fen = record.getRechargeMoney().multiply(new BigDecimal("100"))
                        .stripTrailingZeros().toPlainString();
                if( fen.equals(payAmount)){
                    //金额一致
                    if("10".equals(payResult)){
                        //成功
                        rows = accountMapper.updateAvailableMoneyByRecharge(record.getUid(),record.getRechargeMoney());
                        if(rows < 1 ){
                            throw new RuntimeException("充值更新资金账号失败");
                        }
                        //更新充值记录的状态
                        rows = rechargeRecordMapper.updateStatus(record.getId(),StandardConstant.RECHARGE_STATUS_SUCCESS);
                        if( rows < 1) {
                            throw new RuntimeException("充值更新充值记录状态失败");
                        }
                        result  = 1;//成功
                    } else {
                        //充值失败
                        //更新充值记录的状态
                        rows = rechargeRecordMapper.updateStatus(record.getId(),StandardConstant.RECHARGE_STATUS_FAIL);
                        if( rows < 1) {
                            throw new RuntimeException("充值更新充值记录状态失败");
                        }
                        result = 2;//充值结果是失败的
                    }
                } else {
                    result = 4;//金额不一样
                }
            } else {
                result = 3;//订单已经处理过了
            }
        }
        return result;
    }
}
