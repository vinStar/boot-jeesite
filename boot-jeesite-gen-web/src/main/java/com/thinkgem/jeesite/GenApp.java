package com.thinkgem.jeesite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


/**
 * Spring boot启动类
 *
 * @author OAO
 */
@EnableCaching
@SpringBootApplication
public class GenApp extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(GenApp.class, args);

    }

}
