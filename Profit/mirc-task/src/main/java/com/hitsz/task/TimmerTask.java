package com.hitsz.task;

import com.Hitsz.api.service.IncomeService;
import com.Hitsz.common.utils.HttpClientsUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("taskManger")
public class TimmerTask {
    @DubboReference(interfaceClass = IncomeService.class,version = "1.0")
    private IncomeService incomeService;

    /*生成收益计划*/
    @Scheduled(cron = "0 0 1 * * ?")
    public void invodeGeneratePlan(){
        incomeService.geneteIncomePlan();
    }

    /*收益返还*/
    @Scheduled(cron = "0 0 2 * * ?")
    public void invokeIncomeBack(){
        incomeService.ReturnIncome();
    }

    /**补单接口*/
    // @Scheduled(cron = "0 0/20 * * * ?")
    public void invokeKuaiQianQuery(){
        try{
            String url = "http://localhost:9000/pay/kq/rece/query";
            HttpClientsUtil.doGet(url);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
