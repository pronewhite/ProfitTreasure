package com.hitsz.mircdataservice.mapper;

import com.Hitsz.api.model.RechargeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RechargeRecordMapper {

    //根据用户id查询用户投资情况
    List<RechargeRecord> selectUserRechargeInfo(@Param("uid") Integer uid, @Param("offSet") Integer offSet, @Param("pageSize") Integer pageSize);





    int deleteByPrimaryKey(Integer id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);


    RechargeRecord selectByRechargeNo(@Param("orderId") String orderId);

    int updateStatus(@Param("id") Integer id, @Param("rechargeStatusSuccess") int rechargeStatusSuccess);
}