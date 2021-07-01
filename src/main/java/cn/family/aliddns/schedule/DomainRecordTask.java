package cn.family.aliddns.schedule;

import cn.family.aliddns.service.IAliYunDDnsService;
import cn.family.aliddns.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
