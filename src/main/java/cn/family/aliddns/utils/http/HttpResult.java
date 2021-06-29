package cn.family.aliddns.utils.http;

import lombok.Data;

/**
 * 版权所有(C) SEPHORA 2018-2028
 * Copyright 2018-2028 SEPHORA
 * 创建日期：2019-01-08 18:02
 * 创建人：Andy.Yang
 */
@Data
public class HttpResult {

    //响应码
    private Integer code;

    //响应体
    private String body;

}
