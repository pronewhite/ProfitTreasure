package com.Hitsz.common.enums;

/**
 * Package:com.bjpowernode.common.enums
 * Date:2022/3/3 15:30
 */
public enum RCode {

    UNKOWN(0,"请稍候重试"),
    SUCC(1000,"请求成功"),
    REQUEST_PARAM_ERR(1001,"请求参数有误"),
    REQUEST_PRODUCT_TYPE_ERR(1002,"产品类型有误"),
    PRODUCT_OFFLINE(1003,"产品已经下线"),
    PHONE_EXISTS(1004,"手机号已经注册"),
    PHONE_PATTERN_ERROR(1005,"手机号格式错误"),
    SMS_CODE_INVALID(1006,"验证码无效"),
    PASSWORD_PATTERN_ERROR(1007,"密码格式错误"),
    SMS_CODE_CAN_USE(1008,"验证码处于有效期内"),
    PHONE_OR_PASSWORD_ERROR(1009,"手机号或者密码有误"),
    REALNAME_FAIL(1010,"实名认证失败，请检查信息"),
    REALNAME_HAVEDONE(1011,"实名认证已经通过，请执行其他操作"),
    TOKEN_INVALID(3000,"Token无效")
    ;
    RCode(int c, String t){
        this.code = c;
        this.text = t;
    }

    /**应答码
     * 0：默认
     * 1000-2000是请求参数有误，逻辑的问题
     * 2000-3000是服务器请求错误。
     * 3000-4000是访问dubbo的应答结果
     */
    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
