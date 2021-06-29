package cn.family.aliddns.service.impl;

import cn.family.aliddns.model.HostIPModel;
import cn.family.aliddns.service.HttpClientService;
import cn.family.aliddns.service.IAliYunDDnsService;
import cn.family.aliddns.utils.FastJsonUtils;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 版权所有(C) SEPHORA 2020-2030
 * Copyright 2020-2030 SEPHORA
 * 创建日期：2021-06-29 16:35
 * 创建人：Andy.Yang
 */
@Service
public class AliYunDDnsServiceImpl implements IAliYunDDnsService {

    //参考地址： https://help.aliyun.com/document_detail/29776.html?spm=a2c4g.11186623.6.672.50726b5153DkMW


    //获取公网IP地址URL
    private String CHECK_URL = "https://jsonip.com";


    private String DescribeDomainRecords = "http://alidns.aliyuncs.com";

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

    @Override
    public List<Object> findDescribeDomainRecordsByDomainName(String domainName) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou","LTAI5tNZeXWhCeMjXSy7mcCk","QnMEwDyDC7jkV06UDbtMRIB63jPEUk");
        IAcsClient client = new DefaultAcsClient(profile);

        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
        request.setDomainName("family-cloud.cn");

        try {
            DescribeDomainRecordsResponse response = client.getAcsResponse(request);
            String jsonDate = FastJsonUtils.convertObjectToJSON(response.getDomainRecords());

            System.out.println(jsonDate);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }

        return null;
    }


}
