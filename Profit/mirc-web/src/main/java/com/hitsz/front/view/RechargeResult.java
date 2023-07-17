package com.hitsz.front.view;

import com.Hitsz.api.model.RechargeRecord;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;

public class RechargeResult {

    private String rechargeDesc;
    private BigDecimal rechargeMoney;
    private String rechargeTime;

    public RechargeResult(RechargeRecord record) {

        this.rechargeMoney = record.getRechargeMoney();

        if(record.getRechargeTime() != null){
            this.rechargeTime = DateFormatUtils.format(record.getRechargeTime(),"yyyy-MM-dd HH:mm:ss");
        }

        switch (record.getRechargeStatus()){
            case 0: {
                rechargeDesc = "充值中";
                break;
            }
            case 1: {
                rechargeDesc = "充值成功";
                break;
            }
            case 2:{
                rechargeDesc = "充值失败";
                break;
            }
        }
    }

    public String getRechargeDesc() {
        return rechargeDesc;
    }

    public void setRechargeDesc(String rechargeDesc) {
        this.rechargeDesc = rechargeDesc;
    }

    public BigDecimal getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(BigDecimal rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getRechargeTime() {
        return rechargeTime;
    }

    public void setRechargeTime(String rechargeTime) {
        this.rechargeTime = rechargeTime;
    }
}
