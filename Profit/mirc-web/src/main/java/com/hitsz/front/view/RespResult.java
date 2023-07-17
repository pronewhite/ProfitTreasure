package com.hitsz.front.view;

import com.Hitsz.common.enums.RCode;

import java.util.List;

/**
 * 后台返回数据的统一格式
 */
public class RespResult {
    //应答码，自定义的数字
    private int code;
    //code的文字说明，一般做提示给用户看
    private String msg;
    //单个数据
    private Object data;
    //集合数据
    private List list;
    //分页
    private PageInfo page;
    //根据用户信息生成的Token
    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    //表示成功的RespResult对象
    public static RespResult ok(){
        RespResult result = new RespResult();
        result.setRCode(RCode.SUCC);
        return result;
    }
    //表示失败的RespResult对象
    public static RespResult fail(){
        RespResult result = new RespResult();
        result.setRCode(RCode.UNKOWN);
        return result;
    }

    public void setRCode(RCode rcode){
        this.code = rcode.getCode();
        this.msg = rcode.getText();
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public PageInfo getPage() {
        return page;
    }

    public void setPage(PageInfo page) {
        this.page = page;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
