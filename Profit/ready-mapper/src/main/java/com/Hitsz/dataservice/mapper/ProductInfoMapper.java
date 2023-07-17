package com.Hitsz.dataservice.mapper;

import com.Hitsz.api.model.ProductInfo;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);
}