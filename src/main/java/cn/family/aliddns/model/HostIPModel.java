package cn.family.aliddns.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 版权所有(C) SEPHORA 2020-2030
 * Copyright 2020-2030 SEPHORA
 * 创建日期：2021-06-29 17:41
 * 创建人：Andy.Yang
 */

@Data
public class HostIPModel {

    @JSONField(name = "ip")
    private String ip;

    @JSONField(name = "geo-ip")
    private String geoIp;

    @JSONField(name = "API Help")
    private String apiHelp;
}
