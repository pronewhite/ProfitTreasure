package com.Hitsz.api.service;

import com.Hitsz.api.model.ProductInfo;
import com.Hitsz.api.pojo.MutiProductInfo;

import java.util.List;

public interface ProductInfoService {

    /**
     * 查询每个产品发布时间前三的产品名称
     * @return 三种产品发布时间前三的产品信息
     */
    MutiProductInfo queryProductInfoByTypeLimit();

    /**
     * 根据产品类型查询记录数
     * @param pType 产品类型
     * @return 总记录数
     */
    Integer queryProductRecordByType(Integer pType);

    /**
     * 根据类型，查询位置，查询数量 查询产品信息
     * @param pType 产品类型
     * @param start 起始位置
     * @param pageSize 查询数量
     * @return 产品集合
     */
    List<ProductInfo> querySingleProductInfoByTypeLimit(Integer pType,Integer start,Integer pageSize);

    /**
     * 根据产品ID查询产品信息
     * @param ProductId 产品ID
     * @return 产品信息
     */
    ProductInfo queryProductInfoByProductId(Integer ProductId);

}
