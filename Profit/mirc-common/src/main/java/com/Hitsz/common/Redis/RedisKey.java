package com.Hitsz.common.Redis;

/**
 * 定义Redis中的key
 */
public class RedisKey {

    public static final String KEY_INVEST_RANK = "INVEST:RANK";

    public static final String KEY_SMS_CODE_REG = "SMS:REG:";

    public static final String KEY_SMS_CODE_LOGIN = "SMS:LOGIN:";

    /*redis自增*/
    public static final String KEY_RECHARGE_ORDERID = "RECHARGE:ORDERID:SEQ";

    /*orderId*/
    public static final String KEY_ORDERID_SET= "RECHARGE:ORDERID:SET";
}
