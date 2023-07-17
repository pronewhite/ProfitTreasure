package com.Hitsz.api.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 封装投资栏信息
 */
public class InvestInfo implements Serializable {

    private Integer bid;
    private String phone;
    private String bidTime;
    private BigDecimal bidMoney;

    public InvestInfo() {
    }

    public InvestInfo(Integer bid,String phone, String bidTime, BigDecimal bidMoney) {
        this.bid = bid;
        this.phone = phone;
        this.bidTime = bidTime;
        this.bidMoney = bidMoney;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBidTime() {
        return bidTime;
    }

    public void setBidTime(String bidTime) {
        this.bidTime = bidTime;
    }

    public BigDecimal getBidMoney() {
        return bidMoney;
    }

    public void setBidMoney(BigDecimal bidMoney) {
        this.bidMoney = bidMoney;
    }
}
