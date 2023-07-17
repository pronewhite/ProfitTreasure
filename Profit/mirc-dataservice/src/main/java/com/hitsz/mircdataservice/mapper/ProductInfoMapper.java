package com.hitsz.mircdataservice.mapper;

import com.Hitsz.api.model.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ProductInfoMapper {

    /**
     * 查询平台基本信息：年利率
     * @return
     */
    BigDecimal selectAvgRate();

    /**
     * 根据产品类型查询产品记录总数
     * @param pType 产品类型
     * @return 产品总记录数
     */
    int selectProductRecordByType(@Param("pType") Integer pType);

    /**
     * 根据类型，查询发布时间前三的产品
     * @param type 产品类型
     * @param start 查询初始位置
     * @param offSet 查询数量
     * @return 产品集合
     */
    List<ProductInfo> selectProductByTypeLimit(@Param("type") Integer type,
                                               @Param("start") Integer start,
                                               @Param("offSet") Integer offSet);

    /** 查询需要生成收益计划的理财产品 */
    List<ProductInfo> selectFulledTimeProduct(@Param("begin") Date begin, @Param("end") Date end);






    int deleteByPrimaryKey(Integer id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    int updateProductInfo(@Param("proId") Integer proId);

    int updateProductMoney(@Param("proId") Integer proId, @Param("money") BigDecimal money);


    int updateProductStatus(@Param("id") Integer id, @Param("productStatus") int productStatus);
}