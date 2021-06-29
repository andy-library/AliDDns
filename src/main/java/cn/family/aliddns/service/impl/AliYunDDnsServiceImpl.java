package cn.family.aliddns.service.impl;

import cn.family.aliddns.model.HostIPModel;
import cn.family.aliddns.service.HttpClientService;
import cn.family.aliddns.service.IAliYunDDnsService;
import cn.family.aliddns.utils.FastJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 版权所有(C) SEPHORA 2020-2030
 * Copyright 2020-2030 SEPHORA
 * 创建日期：2021-06-29 16:35
 * 创建人：Andy.Yang
 */
@Service
public class AliYunDDnsServiceImpl implements IAliYunDDnsService {

    //获取公网IP地址URL
    private String CHECK_URL = "https://jsonip.com";

    @Autowired
    private HttpClientService httpClientService;

    @Override
    public String getCurrentHostIP() {
        try {
            String jsonData = httpClientService.doGet(CHECK_URL);
            //解析返回的结果集
            HostIPModel hostIPModel = FastJsonUtils.convertJsonToObject(jsonData,HostIPModel.class);
            //判断非空并返回IP地址
            if(null != hostIPModel && null != hostIPModel.getIp() && !"".equals(hostIPModel.getIp())){
                return hostIPModel.getIp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
