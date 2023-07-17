package com.Hitsz.common.settings;

public class StandardConstant {

    //成功
    public static final int RETURN_STATUS_SUCCESS = 1;
    //失败
    public static final int RETURN_STATUS_FAILURE = 0;

    /**-----------产品状态------------*/
    //可以投标
    public static final int PRODUCT_STATUS_CANINVEST = 0;
    //满标
    public static final int PRODUCT_STATUS_MANBIAO = 1;
    //满标并且使用
    public static final int PRODUCT_STATUS_MANBIAO_USERD = 2;

    /**-----------收益状态------------*/
    public static final int INCOME_STATUS_PLAN = 0;
    public static final int INCOME_STATUS_BACK = 1;

    /**-----------充值状态------------*/
    public static final int RECHARGE_STATUS_PROCESSING = 0;
    public static final int RECHARGE_STATUS_SUCCESS = 1;
    public static final int RECHARGE_STATUS_FAIL = 2;

}
