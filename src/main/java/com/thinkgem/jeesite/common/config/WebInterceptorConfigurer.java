package com.thinkgem.jeesite.common.config;

import com.thinkgem.jeesite.modules.sys.interceptor.LogInterceptor;
import com.thinkgem.jeesite.modules.sys.interceptor.LogThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by vin on 2018/5/16.
 */
@Configuration
public class WebInterceptorConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    private LogInterceptor logInterceptor;
    @Autowired
    private LogThread logThread;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
        logThread.start();
    }
}
