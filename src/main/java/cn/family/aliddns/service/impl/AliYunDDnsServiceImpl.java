package cn.family.aliddns.service.impl;

import cn.family.aliddns.model.DomainRecords;
import cn.family.aliddns.model.HostIPModel;
import cn.family.aliddns.service.HttpClientService;
import cn.family.aliddns.service.IAliYunDDnsService;
import cn.family.aliddns.utils.FastJsonUtils;
import com.alibaba.fastjson.TypeReference;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsRequest;
import com.aliyuncs.alidns.model.v20150109.DescribeDomainRecordsResponse;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordRequest;
import com.aliyuncs.alidns.model.v20150109.UpdateDomainRecordResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
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

    @Value("${cn.aliyun.regionId}")
    private String regionId;

    @Value("${cn.aliyun.accessKey}")
    private String accessKey;

    @Value("${cn.aliyun.accessSecret}")
    private String accessSecret;

    @Value("${cn.config.url}")
    private String url;

    @Value("${cn.config.domainName}")
    private String domainName;

    @Autowired
    private HttpClientService httpClientService;

    /**
     * 统一SDK Client获取
     *
     * @return
     */
    private IAcsClient getDefaultAcsClient() {
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessSecret);
        return new DefaultAcsClient(profile);
    }

    @Override
    public String getCurrentHostIP() {
        try {
            String jsonData = httpClientService.doGet(url);
            //解析返回的结果集
            HostIPModel hostIPModel = FastJsonUtils.convertJsonToObject(jsonData, HostIPModel.class);
            //判断非空并返回IP地址
            if (null != hostIPModel && null != hostIPModel.getIp() && !"".equals(hostIPModel.getIp())) {
                return hostIPModel.getIp();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<DomainRecords> findDescribeDomainRecordsByDomainName(String domainName) {
        List<DomainRecords> domainList = null;
        try {
            IAcsClient client = getDefaultAcsClient();
            DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
            request.setDomainName(domainName);

            DescribeDomainRecordsResponse response = client.getAcsResponse(request);
            String jsonDate = FastJsonUtils.convertObjectToJSON(response.getDomainRecords());
            if (null != jsonDate) {
                domainList = FastJsonUtils.convertJsonToObject(jsonDate, new TypeReference<List<DomainRecords>>() {});
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
        return domainList;
    }

    @Override
    public void updateDescribeDomainRecordsByRecordId(DomainRecords records) {
        try {
            IAcsClient client = getDefaultAcsClient();

            UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
            request.setRecordId(records.getRecordId());
            request.setRR(records.getRr());
            request.setType(records.getType());
            request.setValue(records.getValue());

            UpdateDomainRecordResponse response = client.getAcsResponse(request);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            System.out.println("ErrCode:" + e.getErrCode());
            System.out.println("ErrMsg:" + e.getErrMsg());
            System.out.println("RequestId:" + e.getRequestId());
        }
    }

    @Override
    public void manageDescribeDomainRecords() {
        //获取当前公网IP
        String currentIp = this.getCurrentHostIP();

        if(null != currentIp){
            //获取当前域名解析列表
            List<DomainRecords> currentRecords = this.findDescribeDomainRecordsByDomainName(domainName);

            if(null != currentRecords){
                currentRecords.forEach(records -> {
                    if(!currentIp.equals(records.getValue())){
                        records.setValue(currentIp);
                        this.updateDescribeDomainRecordsByRecordId(records);
                    }
                });
            }
        }

    }
}
