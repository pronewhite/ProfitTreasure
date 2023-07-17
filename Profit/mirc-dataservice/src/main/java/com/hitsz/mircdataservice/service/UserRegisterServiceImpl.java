package com.hitsz.mircdataservice.service;

import com.Hitsz.api.model.FinanceAccount;
import com.Hitsz.api.model.User;
import com.Hitsz.api.pojo.UserInvestInfo;
import com.Hitsz.api.service.UserRegisterService;
import com.Hitsz.common.utils.CommonsUtils;
import com.hitsz.mircdataservice.mapper.FinanceAccountMapper;
import com.hitsz.mircdataservice.mapper.UserMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

@DubboService(interfaceClass = UserRegisterService.class,version = "1.0")
public class UserRegisterServiceImpl implements UserRegisterService {

    @Resource
    private FinanceAccountMapper financeAccountMapper;
    @Resource
    private UserMapper userMapper;


    @Value("${myMoney.smsRegister.code}")
    private String passwordSalt;
    @Override
    public User UserTryRegister(String phone) {
        return userMapper.selectUserInfoByPhone(phone);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized int UserRegister(String phone, String paCode) {
        int registerResult = 0;//默认参数有问题

        if(phone != null && phone.length() == 11 &&(paCode != null && paCode.length() == 32)){

            User queryUser = userMapper.selectUserInfoByPhone(phone);
            if(queryUser == null){
                //注册密码的md5二次加密。 给原始的密码加盐（salt)
                String newPassword = DigestUtils.md5Hex( paCode + passwordSalt);

                User user = new User();
                user.setPhone(phone);
                user.setLoginPassword(newPassword);
                user.setAddTime(new Date());
                userMapper.insertByUser(user);

                //获取主键user.getId()
                FinanceAccount account = new FinanceAccount();
                account.setUid(user.getId());
                account.setAvailableMoney(new BigDecimal("100089"));
                financeAccountMapper.insertSelective(account);

                registerResult = 1;
            }else {
                registerResult = 2;
            }
        }
        return registerResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User userLogin(String phone, String psCode) {
        User user = null;
        if(CommonsUtils.judgePhoneIsCorrect(phone) && (psCode != null && psCode.length() == 32)){
            String newPassword =  DigestUtils.md5Hex( psCode + passwordSalt);
            user = userMapper.selectUserInLogin(phone,newPassword);
            if(user != null){
                user.setLastLoginTime(new Date());
                userMapper.updateByPrimaryKeySelective(user);
            }
        }
        return user;
    }

    @Override
    public boolean modifyUser(String phone, String name, String idCard) {

        int updateResult = 0;

        if(!StringUtils.isAnyBlank(phone,name,idCard)){
           updateResult = userMapper.updateRealName(phone,name,idCard);
        }
        return updateResult > 0;
    }

    @Override
    public User queryUserByphone(String phone) {
        return userMapper.selectUserInfoByPhone(phone);
    }

    @Override
    public UserInvestInfo queryUserInvestINfoByUid(Integer uid) {
        return userMapper.selectUserInvestInfoByUid(uid);
    }

    @Override
    public User queryByUid(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }

    @Override
    public User queryById(Integer uid) {
        return userMapper.selectByPrimaryKey(uid);
    }
}
