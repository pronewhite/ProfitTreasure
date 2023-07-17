package com.Hitsz.api.service;

import com.Hitsz.api.model.User;
import com.Hitsz.api.pojo.UserInvestInfo;

public interface UserRegisterService {

    /** 根据用户输入的手机号完成注册*/
    User UserTryRegister(String phone);

    /** 完成注册*/
    int UserRegister(String phone,String paCode);

    User userLogin(String phone, String psCode);

    /** 更新实名认证信息*/
    boolean modifyUser(String phone, String name, String idCard);

    User queryUserByphone(String phone);

    UserInvestInfo queryUserInvestINfoByUid(Integer uid);

    //根据用户id查询用户信息
    User queryByUid(Integer uid);

    User queryById(Integer uid);
}
