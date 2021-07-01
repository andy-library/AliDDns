package cn.family.aliddns.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class HostIPModel {

    @JSONField(name = "ip")
    private String ip;

    @JSONField(name = "geo-ip")
    private String geoIp;

    @JSONField(name = "API Help")
    private String apiHelp;
}
