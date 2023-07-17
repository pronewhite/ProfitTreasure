package com.hitsz.mircdataservice.mapper;

import com.Hitsz.api.model.IncomeRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface IncomeRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IncomeRecord record);

    int insertSelective(IncomeRecord record);

    IncomeRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IncomeRecord record);

    int updateByPrimaryKey(IncomeRecord record);

    /*找出收益到期的产品*/
    List<IncomeRecord> selectExipredProduct(@Param("yesDate") Date yesDate);
}