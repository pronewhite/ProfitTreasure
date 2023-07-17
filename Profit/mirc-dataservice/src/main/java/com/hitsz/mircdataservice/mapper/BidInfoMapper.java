package com.hitsz.mircdataservice.mapper;

import com.Hitsz.api.model.BidInfo;
import com.Hitsz.api.pojo.InvestInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BidInfoMapper {

    /**
     * 查统计成交金额
     * @return
     */
    BigDecimal selectAllsuccessedBidMoney();

    /**
     * 根据产品ID查询产品投资信息
     * @param productId 产品ID
     * @param start 查询起始位置
     * @param cows 查询数量
     * @return 投资信息集合
     */
    List<InvestInfo> selectInvestInfoByProductId(@Param("productId") Integer productId, @Param("start") Integer start, @Param("cows") Integer cows);





    int deleteByPrimaryKey(Integer id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);


    /** 根据产品Id查询产品投资记录 */
    List<BidInfo> selectProductByProId(@Param("proId") Integer proId);
}