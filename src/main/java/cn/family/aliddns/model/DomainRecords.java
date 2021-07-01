package cn.family.aliddns.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

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
