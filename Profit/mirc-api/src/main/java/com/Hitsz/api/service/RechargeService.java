package com.Hitsz.api.service;

import com.Hitsz.api.model.RechargeRecord;

import java.util.List;

public interface RechargeService {

    List<RechargeRecord> queryRechargeInfoByUid(Integer uid, Integer pageNo, Integer cows);

    int addRechargeRecord(RechargeRecord record);

    int handleKQNotify(String orderId, String payAmount, String payResult);
}
