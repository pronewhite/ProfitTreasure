package com.hitsz.mircdataservice.mapper;

import com.Hitsz.api.model.User;
import com.Hitsz.api.pojo.UserInvestInfo;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /**
     * 查询所有注册人数
     * @return
     */
    int selectAllRegisterUsers();

    /**
     *  根据用户手机号查询用户信息
     * @param phone 手机号
     * @return 用户信息
     */
    User selectUserInfoByPhone(String phone);

    /**
     * 插入用户
     * @param user 用户信息
     */
    int insertByUser(User user);


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);


    User selectUserInLogin(@Param("phone") String phone, @Param("LoginPassword") String LoginPassword);


    int updateRealName(@Param("phone") String phone, @Param("name") String name, @Param("idCard") String idCard);

    //根据用户id查询用户的详细信息
    UserInvestInfo selectUserInvestInfoByUid(@Param("uid") Integer uid);
}