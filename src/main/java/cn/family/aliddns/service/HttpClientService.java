package cn.family.aliddns.service;

import cn.family.aliddns.utils.http.HttpResult;
import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 版权所有(C) SEPHORA 2018-2028
 * Copyright 2018-2028 SEPHORA
 * 创建日期：2019-01-08 18:04
 * 创建人：Andy.Yang
 */

public interface HttpClientService {

    /**
     * GET方法(URL)
     * @param url : 访问路径
     * @return
     * @throws Exception
     */
    String doGet(String url) throws Exception;

    /**
     * GET方法(URL、Headers)
     * @param url : 访问路径
     * @param headers : 头文件
     * @return
     * @throws Exception
     */
    String doGet(String url, Map<String, String> headers) throws Exception;

    /**
     * GET方法(URL、Headers)
     * @param url : 访问路径
     * @param params : 查询参数
     * @return
     * @throws Exception
     */
    String doGet(String url, LinkedHashMap<String, Object> params) throws Exception;

    /**
     * GET方法(URL、Headers、Params)
     * @param url : 访问路径
     * @param headers : 头文件
     * @param params : 查询参数
     * @return
     * @throws Exception
     */
    String doGet(String url, Map<String, String> headers, LinkedHashMap<String, Object> params) throws Exception;

    /**
     * POST请求(url)
     * @param url ： 访问路径
     * @return
     * @throws Exception
     */
    HttpResult doPost(String url) throws Exception;

    /**
     * POST请求(url,headers)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @return
     * @throws Exception
     */
    HttpResult doPost(String url, Map<String, String> headers) throws Exception;

    /**
     * POST请求(url,headers,url)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @param params ： 查询参数
     * @return
     * @throws Exception
     */
    HttpResult doPost(String url, Map<String, String> headers, Map<String, Object> params) throws Exception;

    /**
     * PUT请求(url)
     * @param url ： 访问路径
     * @return
     * @throws Exception
     */
    HttpResult doPut(String url) throws Exception;

    /**
     * PUT请求(url,headers)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @return
     * @throws Exception
     */
    HttpResult doPut(String url, Map<String, String> headers) throws Exception;

    /**
     * PUT请求(url,headers,url)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @param params ： 查询参数
     * @return
     * @throws Exception
     */
    HttpResult doPut(String url, Map<String, String> headers, Map<String, Object> params) throws Exception;

    /**
     * 提交json数据(url)
     * @param url ： 访问路径
     * @return
     * @throws Exception
     */
    HttpResult doPostJson(String url) throws Exception;

    /**
     * 提交json数据(url,headers)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @return
     * @throws Exception
     */
    HttpResult doPostJson(String url, Map<String, String> headers) throws Exception;

    /**
     * 提交json数据
     * @param url
     * @param headers
     * @param json
     * @return
     * @throws Exception
     */
    HttpResult doPostJson(String url, Map<String, String> headers, String json) throws Exception;

    /**
     * 提交json数据
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    HttpResult doPostJsonObject(String url, Map<String, String> headers, JSONObject json) throws Exception;

    /**
     * 绕过验证
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException;
}
