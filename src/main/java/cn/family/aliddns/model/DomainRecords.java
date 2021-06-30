package cn.family.aliddns.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * 版权所有(C) SEPHORA 2020-2030
 * Copyright 2020-2030 SEPHORA
 * 创建日期：2021-06-30 11:06
 * 创建人：Andy.Yang
 */
@Data
public class DomainRecords {

    @JSONField(name = "domainName")
    private String domainName;

    @JSONField(name = "recordId")
    private String recordId;

    @JSONField(name = "rR")
    private String rr;

    @JSONField(name = "value")
    private String value;

    @JSONField(name = "type")
    private String type;

}
