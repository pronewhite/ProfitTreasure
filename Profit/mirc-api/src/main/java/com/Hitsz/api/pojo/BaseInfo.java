package com.Hitsz.api.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class BaseInfo implements Serializable {
    //利率
    private BigDecimal historyAvgRate;
    //人数
    private Integer registerUsers;
    //累计成交金额
    private BigDecimal sumBidMoney;

    public BaseInfo() {
    }

    public BaseInfo(BigDecimal historyAvgRate, Integer registerUsers, BigDecimal sumBidMoney) {
        this.historyAvgRate = historyAvgRate;
        this.registerUsers = registerUsers;
        this.sumBidMoney = sumBidMoney;
    }

    @Override
    public String toString() {
        return "BaseInfo{" +
                "historyAvgRate=" + historyAvgRate +
                ", registerUsers=" + registerUsers +
                ", sumBidMoney=" + sumBidMoney +
                '}';
    }

    public BigDecimal getHistoryAvgRate() {
        return historyAvgRate;
    }

    public void setHistoryAvgRate(BigDecimal historyAvgRate) {
        this.historyAvgRate = historyAvgRate;
    }

    public Integer getRegisterUsers() {
        return registerUsers;
    }

    public void setRegisterUsers(Integer registerUsers) {
        this.registerUsers = registerUsers;
    }

    public BigDecimal getSumBidMoney() {
        return sumBidMoney;
    }

    public void setSumBidMoney(BigDecimal sumBidMoney) {
        this.sumBidMoney = sumBidMoney;
    }
}
