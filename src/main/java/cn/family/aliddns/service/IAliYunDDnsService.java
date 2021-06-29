package cn.family.aliddns.service;

import java.util.List;

/**
 * 版权所有(C) SEPHORA 2020-2030
 * Copyright 2020-2030 SEPHORA
 * 创建日期：2021-06-29 16:34
 * 创建人：Andy.Yang
 */

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
    List<Object> findDescribeDomainRecordsByDomainName(String domainName);

}
