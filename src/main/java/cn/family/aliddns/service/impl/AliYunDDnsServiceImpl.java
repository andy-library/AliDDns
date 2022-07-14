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
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AliYunDDnsServiceImpl implements IAliYunDDnsService {

    //参考地址： https://help.aliyun.com/document_detail/29776.html?spm=a2c4g.11186623.6.672.50726b5153DkMW

    @Value("${cn.aliyun.regionId}")
    private String regionId;

    @Value("${cn.aliyun.accessKey}")
    private String accessKey;

    @Value("${cn.aliyun.accessSecret}")
    private String accessSecret;

    @Value("${cn.config.check_url}")
    private String check_url;

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
        if(!StringUtils.isEmpty(regionId) && !StringUtils.isEmpty(accessKey) && !StringUtils.isEmpty(accessSecret)){
            DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessSecret);
            return new DefaultAcsClient(profile);
        }
        return null;
    }

    @Override
    public String getCurrentHostIP() {
        if(null != check_url && !"".equals(check_url)){
            try {
                String jsonData = httpClientService.doGet(check_url.trim());
                //解析返回的结果集
                HostIPModel hostIPModel = FastJsonUtils.convertJsonToObject(jsonData, HostIPModel.class);
                //判断非空并返回IP地址
                if (null != hostIPModel && null != hostIPModel.getIp() && !"".equals(hostIPModel.getIp())) {
                    return hostIPModel.getIp();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public List<DomainRecords> findDescribeDomainRecordsByDomainName(String domainName) {
        List<DomainRecords> domainList = null;
        if(null != domainName && !"".equals(domainName)) {
            try {
                IAcsClient client = this.getDefaultAcsClient();
                if(null != client){
                    DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
                    request.setDomainName(domainName);

                    DescribeDomainRecordsResponse response = client.getAcsResponse(request);
                    String jsonDate = FastJsonUtils.convertObjectToJSON(response.getDomainRecords());
                    if (null != jsonDate) {
                        domainList = FastJsonUtils.convertJsonToObject(jsonDate, new TypeReference<List<DomainRecords>>() {});
                    }
                }
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                System.out.println("ErrCode:" + e.getErrCode());
                System.out.println("ErrMsg:" + e.getErrMsg());
                System.out.println("RequestId:" + e.getRequestId());
            }
        }
        return domainList;
    }

    @Override
    public void updateDescribeDomainRecordsByRecordId(DomainRecords records) {
        if(null != records){
            try {
                IAcsClient client = getDefaultAcsClient();
                if(null != client){
                    UpdateDomainRecordRequest request = new UpdateDomainRecordRequest();
                    request.setRecordId(records.getRecordId());
                    request.setRR(records.getRr());
                    request.setType(records.getType());
                    request.setValue(records.getValue());

                    client.getAcsResponse(request);
                }
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                System.out.println("ErrCode:" + e.getErrCode());
                System.out.println("ErrMsg:" + e.getErrMsg());
                System.out.println("RequestId:" + e.getRequestId());
            }
        }
    }

    @Override
    public void manageDescribeDomainRecords() {
        if(null != domainName && !"".equals(domainName)){
            //获取当前域名解析列表
            List<DomainRecords> currentRecords = this.findDescribeDomainRecordsByDomainName(domainName);
            if(null != currentRecords){
                //获取当前公网IP
                String currentIp = this.getCurrentHostIP();
                //获取公网IP时可能connect timed out,增加一次非空判断
                if(null != currentIp){
                    currentRecords.forEach(records -> {
                        System.out.println("替换前: " + records.getRr() + "." + records.getDomainName() + " : " + records.getValue());
                        //如果已解析的IP和当前公网IP不符,则更新
                        if(!currentIp.equals(records.getValue())){
                            records.setValue(currentIp);
                            this.updateDescribeDomainRecordsByRecordId(records);
                        }
                    });
                }
            }
        }
    }
}
