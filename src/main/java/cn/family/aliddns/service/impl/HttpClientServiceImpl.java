package cn.family.aliddns.service.impl;

import cn.family.aliddns.service.HttpClientService;
import cn.family.aliddns.utils.http.HttpResult;
import com.alibaba.fastjson.JSONObject;
import com.google.common.net.UrlEscapers;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 版权所有(C) SEPHORA 2018-2028
 * Copyright 2018-2028 SEPHORA
 * 创建日期：2019-01-08 18:04
 * 创建人：Andy.Yang
 */
@Service
public class HttpClientServiceImpl implements HttpClientService {

    @Autowired
    private CloseableHttpClient httpClient;

    @Autowired
    private RequestConfig config;

    /**
     * GET方法(URL)
     * @param url : 访问路径
     * @return
     * @throws Exception
     */
    public String doGet(String url) throws Exception {
        return this.doGet(url, null, null);
    }

    /**
     * GET方法(URL、Headers)
     * @param url : 访问路径
     * @param headers : 头文件
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, String> headers) throws Exception {
        return this.doGet(url, headers, null);
    }

    @Override
    public String doGet(String url, LinkedHashMap<String, Object> params) throws Exception {
        return this.doGet(url, null, params);
    }

    /**
     * GET方法(URL、Headers、Params)
     * @param url : 访问路径
     * @param headers : 头文件
     * @param params : 查询参数
     * @return
     * @throws Exception
     */
    public String doGet(String url, Map<String, String> headers, LinkedHashMap<String, Object> params) throws Exception {

        //构造URL
        URIBuilder uriBuilder = new URIBuilder(url);

        //params,如果有的话
        if (null != params) {
            //遍历map,拼接请求参数
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }

        //创建HTTP GET请求  针对拼接成字符串的URL中特殊字符测处理方法(主要是微信相关的内容)
        HttpGet httpGet = new HttpGet(UrlEscapers.urlFragmentEscaper().escape(uriBuilder.build().toString()));   // NOSONAR
        //装载配置信息
        httpGet.setConfig(config);

        //设置Headers,如果有的话
        if(null != headers){
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpGet.setHeader(header.getKey(), header.getValue());
            }
        }

        //发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpGet);

        //判断状态码是否为200
        if (response.getStatusLine().getStatusCode() == 200) {
            //返回响应体的内容
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        }else if (response.getStatusLine().getStatusCode() != 200){
            //防止僵尸连接出现，此处显式abort
            httpGet.abort();
        }
        return null;
    }

    /**
     * POST请求(url)
     * @param url ： 访问路径
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url) throws Exception {
        return this.doPost(url, null, null);
    }

    /**
     * POST请求(url,headers)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, String> headers) throws Exception {
        return this.doPost(url,headers,null);
    }

    /**
     * POST请求(url,headers,url)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @param params ： 查询参数
     * @return
     * @throws Exception
     */
    public HttpResult doPost(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
        //声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        //加入配置信息
        httpPost.setConfig(config);

        //设置Headers,如果有的话
        if(null != headers){
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }

        //判断map是否为空，不为空则进行遍历，封装from表单对象
        if (params != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            //构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            //把表单放到post里
            httpPost.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPost);

        HttpResult httpResult = new HttpResult();
        httpResult.setCode(response.getStatusLine().getStatusCode());
        httpResult.setBody(EntityUtils.toString(response.getEntity(), "UTF-8"));

        return httpResult;
    }

    /**
     * PUT请求(url)
     * @param url ： 访问路径
     * @return
     * @throws Exception
     */
    public HttpResult doPut(String url) throws Exception {
        return this.doPut(url, null, null);
    }

    /**
     * PUT请求(url,headers)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @return
     * @throws Exception
     */
    public HttpResult doPut(String url, Map<String, String> headers) throws Exception {
        return this.doPut(url,headers,null);
    }

    /**
     * PUT请求(url,headers,url)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @param params ： 查询参数
     * @return
     * @throws Exception
     */
    public HttpResult doPut(String url, Map<String, String> headers, Map<String, Object> params) throws Exception {
        //声明httpPost请求
        HttpPut httpPut = new HttpPut(url);
        //加入配置信息
        httpPut.setConfig(config);

        //设置Headers
        if(null != headers){
            Set<String> keys = headers.keySet();
            for (Iterator<String> i = keys.iterator(); i.hasNext();) {
                String key = (String) i.next();
                httpPut.setHeader(key, headers.get(key));
            }
        }

        // 判断map是否为空，不为空则进行遍历，封装from表单对象
        if (params != null) {
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 构造from表单对象
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");

            // 把表单放到post里
            httpPut.setEntity(urlEncodedFormEntity);
        }

        // 发起请求
        CloseableHttpResponse response = this.httpClient.execute(httpPut);

        HttpResult httpResult = new HttpResult();
        httpResult.setCode(response.getStatusLine().getStatusCode());
        httpResult.setBody(EntityUtils.toString(response.getEntity(), "UTF-8"));

        return httpResult;
    }

    /**
     * 提交json数据(url)
     * @param url ： 访问路径
     * @return
     * @throws Exception
     */
    public HttpResult doPostJson(String url) throws Exception {
        return this.doPostJson(url, null, null);
    }

    /**
     * 提交json数据(url,headers)
     * @param url ： 访问路径
     * @param headers ： 头文件
     * @return
     * @throws Exception
     */
    public HttpResult doPostJson(String url, Map<String, String> headers) throws Exception {
        return this.doPostJson(url,headers,null);
    }

    /**
     * 提交json数据
     * @param url
     * @param headers
     * @param json
     * @return
     * @throws Exception
     */
    public HttpResult doPostJson(String url, Map<String, String> headers, String json) throws Exception {

        //声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        //加入配置信息
        httpPost.setConfig(config);

        //设置Headers
        if(null != headers){
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }

        if (json != null) {
            // 构造一个form表单式的实体
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }

        CloseableHttpResponse response = this.httpClient.execute(httpPost);


        HttpResult httpResult = new HttpResult();
        httpResult.setCode(response.getStatusLine().getStatusCode());
        httpResult.setBody(EntityUtils.toString(response.getEntity(), "UTF-8"));

        return httpResult;
    }

    /**
     * 提交json数据
     * @param url
     * @param json
     * @return
     * @throws Exception
     */
    public HttpResult doPostJsonObject(String url, Map<String, String> headers, JSONObject json) throws Exception {
        //声明httpPost请求
        HttpPost httpPost = new HttpPost(url);
        //加入配置信息
        httpPost.setConfig(config);

        //设置Headers
        if(null != headers){
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpPost.setHeader(header.getKey(), header.getValue());
            }
        }

        if (json != null) {
            // 构造一个form表单式的实体
            StringEntity stringEntity = new StringEntity(json.toString(), ContentType.APPLICATION_JSON);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(stringEntity);
        }

        CloseableHttpResponse response = this.httpClient.execute(httpPost);


        HttpResult httpResult = new HttpResult();
        httpResult.setCode(response.getStatusLine().getStatusCode());
        httpResult.setBody(EntityUtils.toString(response.getEntity(), "UTF-8"));

        return httpResult;
    }

    /**
     * 绕过验证
     * @return
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        /**
         * SSLContext官方解释
         * 该类的实例表示一个安全的套接字协议实现，作为安全套接字工厂或SSLEngines的工厂
         * 该类使用一组可选的密钥和信任管理器以及安全随机字节源进行初始化
         * Java平台的每个实现都需要支持以下标准的SSLContext协议：TLSv1
         */
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                                           String paramString) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sslContext.init(null, new TrustManager[] { trustManager }, null);
        return sslContext;
    }
}
