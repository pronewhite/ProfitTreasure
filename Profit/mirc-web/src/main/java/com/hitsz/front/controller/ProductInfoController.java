package com.hitsz.front.controller;

import com.Hitsz.api.model.ProductInfo;
import com.Hitsz.api.pojo.InvestInfo;
import com.Hitsz.api.pojo.MutiProductInfo;
import com.Hitsz.common.enums.RCode;
import com.hitsz.front.view.PageInfo;
import com.hitsz.front.view.RespResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "显示首页")
@RestController
@RequestMapping("/v1")
public class ProductInfoController extends BaseInfoController{

    @ApiOperation(value = "首页三大产品",notes = "新手宝，优选产品，散选产品")
    @GetMapping("/product/info")
    public RespResult productIndex(){

        RespResult respResult = RespResult.ok();
        MutiProductInfo mutiProductInfo = productInfoService.queryProductInfoByTypeLimit();
        respResult.setData(mutiProductInfo);
        return respResult;
    }

    @ApiOperation(value = "显示单个产品的更多信息", notes = "d用户点击《显示更多》会显示此产品更多详细信息")
    @GetMapping("/product/list")
    public RespResult queryProductByType(@RequestParam("pType") Integer pType,
                                         @RequestParam(value = "pageNo",required = false) Integer pageNo,
                                         @RequestParam(value = "pageSize",required = false) Integer pageSize){
        //RespResult默认是错误
        RespResult respResult = RespResult.fail();
        int counts = productInfoService.queryProductRecordByType(pType);
        if(counts != 0){
            List<ProductInfo> productInfos = productInfoService.querySingleProductInfoByTypeLimit(pType, pageNo, pageSize);
            PageInfo pageInfo = new PageInfo(pageNo,pageSize,counts);
            respResult = RespResult.ok();
            respResult.setList(productInfos);
            respResult.setPage(pageInfo);
        }
        return respResult;
    }


    @ApiOperation(value = "显示产品的投资信息", notes = "显示该产品详细投资信息")
    @GetMapping("/prodInvest/info")
    public RespResult queryProductByProductId(@RequestParam("ProductId") Integer ProductId){

        RespResult respResult = RespResult.fail();
        ProductInfo productInfo = productInfoService.queryProductInfoByProductId(ProductId);
        if(productInfo != null){
            List<InvestInfo> investInfos = investInfoService.queryInvestInfoByProductId(ProductId, 1, 5);
            respResult = RespResult.ok();
            respResult.setData(productInfo);
            respResult.setList(investInfos);
        }else{
            respResult.setRCode(RCode.PRODUCT_OFFLINE);
        }
        return respResult;
    }
}
