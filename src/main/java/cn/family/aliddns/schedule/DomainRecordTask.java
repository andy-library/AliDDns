package cn.family.aliddns.schedule;

import cn.family.aliddns.utils.DateUtils;
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

    @Scheduled(cron = "0 0/1 * * * ?")
    public void execute() {
        System.out.println("执行了一次" + DateUtils.getCurrentSytemTimeForString());
    }
}
