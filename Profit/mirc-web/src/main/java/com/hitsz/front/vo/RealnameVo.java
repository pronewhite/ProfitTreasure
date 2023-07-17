package com.hitsz.front.vo;

import java.io.Serializable;

public class RealnameVo implements Serializable {

    private String name;
    private String phone;
    private String idCard;
    private String code;

    public RealnameVo() {
    }

    public RealnameVo(String name, String phone, String idCard, String code) {
        this.name = name;
        this.phone = phone;
        this.idCard = idCard;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
