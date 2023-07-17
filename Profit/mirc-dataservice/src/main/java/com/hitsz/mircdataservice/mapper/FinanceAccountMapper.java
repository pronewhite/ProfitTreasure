package com.hitsz.mircdataservice.mapper;

import com.Hitsz.api.model.FinanceAccount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface FinanceAccountMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);



    //查询用户账户信息
    FinanceAccount selectUserFiance(@Param("uid") Integer uid);

    int updateUserFinanceInfo(@Param("uid") Integer uid, @Param("money") BigDecimal money);

    /*将收益返还给用户的账户*/
    int updateAccountToAddProfit(@Param("uid") Integer uid, @Param("bidMoney") BigDecimal bidMoney, @Param("incomeMoney") BigDecimal incomeMoney);
    /*更新用户余额*/
    int updateAvailableMoneyByRecharge(@Param("uid") Integer uid, @Param("rechargeMoney") BigDecimal rechargeMoney);
}