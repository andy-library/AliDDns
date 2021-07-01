package cn.family.aliddns.utils.http;

import lombok.Data;

@Data
public class HttpResult {

    //响应码
    private Integer code;

    //响应体
    private String body;

}
