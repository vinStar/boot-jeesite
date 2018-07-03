package com.thinkgem.jeesite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Created by vin on 2018/7/3.
 */
//@RefreshScope 配置动态更新
@EnableDiscoveryClient
@SpringBootApplication
public class SAApp {

    public static void main(String[] args) {
        SpringApplication.run(SAApp.class, args);
    }

}
