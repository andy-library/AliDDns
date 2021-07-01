package cn.family.aliddns.service;

import cn.family.aliddns.model.DomainRecords;
import java.util.List;

public interface IAliYunDDnsService {

    /**
     * 获取当前公网IP地址
     * @return
     */
    String getCurrentHostIP();

    /**
     * 获取当前域名解析列表
     * @param domainName
     * @return
     */
    List<DomainRecords> findDescribeDomainRecordsByDomainName(String domainName);

    /**
     * 修改域名解析地址
     * @param records
     */
    void updateDescribeDomainRecordsByRecordId(DomainRecords records);

    /**
     * 管理DNS解析
     */
    void manageDescribeDomainRecords();

}
