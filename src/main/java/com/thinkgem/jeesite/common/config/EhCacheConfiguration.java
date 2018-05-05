package com.thinkgem.jeesite.common.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by vin on 05/05/2018.
 */
@Configuration
@EnableCaching
public class EhCacheConfiguration {

    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean() {
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }


    @Bean(name = "ehCacheManager")
    public EhCacheCacheManager ehCacheManager(EhCacheManagerFactoryBean bean) {

        return new EhCacheCacheManager(bean.getObject ());
    }
}
