package com.hitsz.front.service;

public interface SmsService {

    /**
     *发送短信
     * @param phone 手机号
     * @return true :发送成功，false:其他情况
     */
    boolean sendSms(String phone);

    /** 验证验证码是否合理*/
    boolean checkPaCodeAndsCode(String phone,String sCode);
}
