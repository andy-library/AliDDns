package cn.family.aliddns.schedule;

import cn.family.aliddns.service.IAliYunDDnsService;
import cn.family.aliddns.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 版权所有(C) SEPHORA 2020-2030
 * Copyright 2020-2030 SEPHORA
 * 创建日期：2021-06-30 16:01
 * 创建人：Andy.Yang
 */
@Component
public class DomainRecordTask {

    @Autowired
    private IAliYunDDnsService aliYunDDnsService;


    @Scheduled(cron = "0 0/15 * * * ?")
    public void execute() {
        aliYunDDnsService.manageDescribeDomainRecords();
        System.out.println("执行了一次" + DateUtils.getCurrentSytemTimeForString());
    }
}
