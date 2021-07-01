package cn.family.aliddns.utils.http;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class HttpClient {

    @Value("${cn.family.server.httpClient.maxTotal}")
    private Integer maxTotal;

    @Value("${cn.family.server.httpClient.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;

    @Value("${cn.family.server.httpClient.connectTimeout}")
    private Integer connectTimeout;

    @Value("${cn.family.server.httpClient.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${cn.family.server.httpClient.socketTimeout}")
    private Integer socketTimeout;

    @Value("${cn.family.server.httpClient.staleConnectionCheckEnabled}")
    private boolean staleConnectionCheckEnabled;

    /**
     * 实例化一个连接池管理器，设置最大连接数、并发连接数
     * @return
     */
    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager(){
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        //最大连接数
        httpClientConnectionManager.setMaxTotal(maxTotal);
        //并发数
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpClientConnectionManager;
    }

    /**
     * 实例化连接池，设置连接池管理器
     * 将httpClientConnectionManager注入
     * @param httpClientConnectionManager
     * @return
     */
    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager){

        //HttpClientBuilder中的构造方法被protected修饰，所以这里不能直接使用new来实例化一个HttpClientBuilder，可以使用HttpClientBuilder提供的静态方法create()来获取HttpClientBuilder对象
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        httpClientBuilder.setConnectionManager(httpClientConnectionManager);

        return httpClientBuilder;
    }

    /**
     * 配置httpclient并设置为多例
     * @param httpClientBuilder
     * @return
     */
    @Bean
    @Scope("prototype")
    public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder httpClientBuilder){
        return httpClientBuilder.build();
    }

    /**
     * 配置Builder,Builder是RequestConfig的一个内部类
     * 通过RequestConfig的custom方法来获取到一个Builder对象
     * 设置builder的连接信息,这里还可以设置proxy，cookieSpec等属性
     * @return
     */
    @Bean(name = "builder")
    @Scope("prototype")
    public RequestConfig.Builder getBuilder(){
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
    }

    /**
     * 配置RequestConfig信息
     * @param builder
     * @return
     */
    @Bean
    public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder){
        return builder.build();
    }

}
