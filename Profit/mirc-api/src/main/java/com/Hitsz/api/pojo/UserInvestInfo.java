package com.Hitsz.api.pojo;

import com.Hitsz.api.model.User;

import java.math.BigDecimal;

public class UserInvestInfo extends User {

    private BigDecimal availableMoney;

    public BigDecimal getAvailableMoney() {
        return availableMoney;
    }

    public void setAvailableMoney(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }

    public UserInvestInfo(BigDecimal availableMoney) {
        this.availableMoney = availableMoney;
    }
}
