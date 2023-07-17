package com.hitsz.mircdataservice.service;

import com.Hitsz.api.model.ProductInfo;
import com.Hitsz.api.pojo.MutiProductInfo;
import com.Hitsz.api.service.PlatBaseInfoService;
import com.Hitsz.api.service.ProductInfoService;
import com.Hitsz.common.settings.ProductTypeConstant;
import com.hitsz.mircdataservice.mapper.ProductInfoMapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@DubboService(interfaceClass = ProductInfoService.class,version = "1.0")
public class productInfoServiceImpl implements ProductInfoService {

    //注入Mapper
    @Resource
    private ProductInfoMapper productInfoMapper;
    @Override
    public MutiProductInfo queryProductInfoByTypeLimit() {

        MutiProductInfo productResult = new MutiProductInfo();

        //查询新手宝
        List<ProductInfo> xinShouBaoList = productInfoMapper.selectProductByTypeLimit(
                                                             ProductTypeConstant.PRODUCT_TYPE_XINSHOUBAO,
                                                             0, 1);
        //查询优选产品
        List<ProductInfo> youXuanProductList = productInfoMapper.selectProductByTypeLimit(
                                                                 ProductTypeConstant.PRODUCT_TYPE_YOUXUAN,
                                                            0, 3);
        //查询散标产品
        List<ProductInfo> sanBiaoProductList = productInfoMapper.selectProductByTypeLimit(
                                                                 ProductTypeConstant.PRODUCT_TYPE_SANBIAO,
                                                                0, 3);
        productResult.setXinShouBao(xinShouBaoList);
        productResult.setYouXuan(youXuanProductList);
        productResult.setSanBiao(sanBiaoProductList);

        return productResult;
    }

    @Override
    public Integer queryProductRecordByType(Integer pType) {
        return productInfoMapper.selectProductRecordByType(pType);
    }

    @Override
    public List<ProductInfo> querySingleProductInfoByTypeLimit(Integer pType, Integer start, Integer pageSize) {

        List<ProductInfo> list = new ArrayList<>();
        int offSet = (start - 1) * pageSize;
        return productInfoMapper.selectProductByTypeLimit(pType,offSet,pageSize);
    }

    @Override
    public ProductInfo queryProductInfoByProductId(Integer ProductId) {
        return productInfoMapper.selectByPrimaryKey(ProductId);
    }
}
