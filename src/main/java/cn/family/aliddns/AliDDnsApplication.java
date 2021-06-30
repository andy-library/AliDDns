package cn.family.aliddns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling   //开启定时任务功能
@SpringBootApplication
public class AliDDnsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliDDnsApplication.class, args);
    }

}
