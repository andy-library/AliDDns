package cn.family.aliddns.utils.http;

import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 版权所有(C) SEPHORA 2018-2028
 * Copyright 2018-2028 SEPHORA
 * 创建日期：2019-01-08 18:01
 * 创建人：Andy.Yang
 */
@Component
public class IdleConnectionEvictor extends Thread {

    @Autowired
    private HttpClientConnectionManager httpClientConnectionManager;

    private volatile boolean shutdown;

    public IdleConnectionEvictor() {
        super();
        super.start();
    }

    @Override
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    wait(5000);
                    //System.out.println("关闭失效的连接......");
                    //关闭失效的连接
                    httpClientConnectionManager.closeExpiredConnections();
                }
            }
        } catch (InterruptedException ex) {
            //结束
        }
    }

    //关闭清理无效连接的线程
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
