package cn.family.aliddns.service;

import cn.family.aliddns.model.DomainRecords;
import cn.family.aliddns.model.HostIPModel;
import cn.family.aliddns.utils.FastJsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 版权所有(C) SEPHORA 2020-2030
 * Copyright 2020-2030 SEPHORA
 * 创建日期：2021-06-29 17:38
 * 创建人：Andy.Yang
 */

@SpringBootTest
public class AliYunDDnsServiceTest {

    @Autowired
    private IAliYunDDnsService aliYunDDnsService;

    @Test
    public void getCurrectHostIP(){
        String jsonData = aliYunDDnsService.getCurrentHostIP();


    }

    @Test
    public void findDomainRecords() {
        aliYunDDnsService.findDescribeDomainRecordsByDomainName("family-cloud.cn");
    }

    @Test
    public void updateDomainRecords() {
        List<DomainRecords> recordsList = aliYunDDnsService.findDescribeDomainRecordsByDomainName("family-cloud.cn");
        if(null != recordsList) {
            String ip = "2.2.2.2";
            for(DomainRecords records : recordsList){
                if("router".equals(records.getRr())){
                    records.setValue(ip);
                    aliYunDDnsService.updateDescribeDomainRecordsByRecordId(records);
                }
            }
        }
    }

}
