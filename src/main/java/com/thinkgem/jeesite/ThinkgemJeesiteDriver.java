package com.thinkgem.jeesite;

import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


/**
 * Spring boot启动类
 * @author OAO
 */
@EnableCaching
@SpringBootApplication
public class ThinkgemJeesiteDriver   extends SpringBootServletInitializer{

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        //set register error pagefilter false
//        //setRegisterErrorPageFilter(false);
//        return application.sources(ThinkgemJeesiteDriver.class);
//    }

    public static void main(String[] args)  throws Exception{

    	SpringApplication.run(ThinkgemJeesiteDriver.class, args);
		SystemService.printKeyLoadMessage();
    }




}
