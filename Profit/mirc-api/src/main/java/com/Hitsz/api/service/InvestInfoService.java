package com.Hitsz.api.service;

import com.Hitsz.api.pojo.InvestInfo;

import java.math.BigDecimal;
import java.util.List;

public interface InvestInfoService {

    /**
     * 根据产品ID查询产品投资信息
     * @param productId 产品ID
     * @param start 查询起始位置
     * @param cows 查询数量
     * @return 投资信息集合
     */
    List<InvestInfo> queryInvestInfoByProductId(Integer productId, Integer start, Integer cows);

    /** 根据uid，proId,money进行投资 */
    int investOperation(Integer uid, Integer proId, BigDecimal money);
}
