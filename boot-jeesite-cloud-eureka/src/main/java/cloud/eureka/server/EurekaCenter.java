package cloud.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Created by vin on 2018/7/2.
 */
@EnableEurekaServer  //启动一个服务注册中心提供给其他应用进行对话
@SpringBootApplication
public class EurekaCenter {
    public static void main(String[] args) {
        SpringApplication.run(EurekaCenter.class, args);
    }
}
