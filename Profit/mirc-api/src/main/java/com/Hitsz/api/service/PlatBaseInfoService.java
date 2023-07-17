package com.Hitsz.api.service;

import com.Hitsz.api.pojo.BaseInfo;

public interface PlatBaseInfoService {
    /**
     * 1.计算利率
     * 2.统计人数
     * 3.计算成交金额
     */
    BaseInfo queryPlatBaseInfo();

}
