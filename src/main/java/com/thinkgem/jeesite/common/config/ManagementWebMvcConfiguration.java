package com.thinkgem.jeesite.common.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Configuration equivalent to {@code @EnableWebMvc} that only allows Spring Boot's
 * own WebMvcConfigurers to be used, preventing application configurers in the parent
 * context from leaking into the child management context.
 * 解决健康监控新端口 与 WebInterceptorConfigurer 启发的 error BeanInstantiationException 问题
 * 参考  https://github.com/spring-projects/spring-boot/issues/4929
 */
@Configuration
public class ManagementWebMvcConfiguration
        extends DelegatingWebMvcConfiguration {

    @Override
    @Autowired(required = false)
    public void setConfigurers(List<WebMvcConfigurer> configurers) {
        List<WebMvcConfigurer> filtered = new ArrayList<WebMvcConfigurer>(
                configurers);
        Iterator<WebMvcConfigurer> iterator = filtered.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getClass().getName()
                    .startsWith("org.springframework.boot")) {
                iterator.remove();
            }
        }
        super.setConfigurers(filtered);
    }
}
