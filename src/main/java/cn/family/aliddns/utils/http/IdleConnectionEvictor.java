package cn.family.aliddns.utils.http;

import org.apache.http.conn.HttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
