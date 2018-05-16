package com.thinkgem.jeesite.common.config;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.security.shiro.cache.JedisCacheManager;
import com.thinkgem.jeesite.common.security.shiro.session.CacheSessionDAO;
import com.thinkgem.jeesite.common.security.shiro.session.JedisSessionDAO;
import com.thinkgem.jeesite.common.security.shiro.session.SessionManager;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shrio配置类
 * <p>Title: ShiroConfiguration</p>
 * <p>Description: </p>
 *
 * @author OAO
 * @version 1.0
 * @date 2017-11-09
 */
@Configuration
@EnableCaching
public class ShiroConfiguration {

    private Logger logger = LoggerFactory.getLogger(getClass());

    //switch:EhCache
    //@Autowired
    //CacheSessionDAO sessionDAO;

    //swtich:redis
    @Autowired
    JedisSessionDAO sessionDAO;

    @Value("${session.sessionTimeout}")
    Long sessionTimeout;
    @Value("${session.sessionTimeoutClean}")
    Long sessionTimeoutClean;
    @Value("${session.simpleCookie}")
    String simpleCookie;

    @Value("${adminPath}")
    String adminPath;

    //身份验证过滤器，拦截 url
    @Bean
    public FormAuthenticationFilter formAuthenticationFilter() {
        return new FormAuthenticationFilter();
    }

    // 拦截后真正的登录实现者
    @Bean
    public SystemAuthorizingRealm systemAuthorizingRealm() {
        return new SystemAuthorizingRealm();
    }

    // 配置访问权限
    @Bean
    public LinkedHashMap<String, String> shiroFilterChainDefinitions() {
        // 配置访问权限
        LinkedHashMap<String, String> linkedHashMap = Maps.newLinkedHashMap();
        linkedHashMap.put("/sso/**", "anon");
        linkedHashMap.put("/static/**", "anon");
        linkedHashMap.put("/Scripts/**", "anon");
        linkedHashMap.put("/userfiles/**", "anon");
        //linkedHashMap.put(adminPath + "/cas", "cas");
        linkedHashMap.put(adminPath + "/login", "anon");
        linkedHashMap.put(adminPath + "/logout", "logout");
        linkedHashMap.put("/a/login", "anon");
        linkedHashMap.put("/**", "anon");
        linkedHashMap.put(adminPath + "/**", "user");
        //linkedHashMap.put("/act/editor/**", "user");
        //linkedHashMap.put("/ReportServer/**", "user");
        return linkedHashMap;
    }


    //    安全认证过滤器
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        logger.debug("sessionTimeout : " + sessionTimeout);
        Map<String, Filter> filters = new HashMap<String, Filter>();
        filters.put("authc", formAuthenticationFilter());

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setFilters(filters);
        bean.setSecurityManager(securityManager());
        bean.setLoginUrl("/a/login");
        bean.setSuccessUrl("/a?login");// 不配置，shior 默认转到根路径 '/'

        bean.setFilterChainDefinitionMap(shiroFilterChainDefinitions());
        return bean;
    }


    //    自定义会话管理配置
    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        SessionManager sessionManager = new SessionManager();
        sessionManager.setSessionDAO(sessionDAO);
        sessionManager.setGlobalSessionTimeout(sessionTimeout);
        sessionManager.setSessionValidationInterval(sessionTimeoutClean);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookie(new SimpleCookie(simpleCookie));
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }


    //    定义Shiro安全管理配置
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(systemAuthorizingRealm());
        defaultWebSecurityManager.setSessionManager(sessionManager());
        defaultWebSecurityManager.setCacheManager(shiroCacheManager());

        return defaultWebSecurityManager;
    }

    // swtich redis
    @Bean
    public org.apache.shiro.cache.CacheManager shiroCacheManager() {
        return new JedisCacheManager();
    }

// swtich EhCache
//    @Bean(name = "shiroCacheManager")
//    public org.apache.shiro.cache.CacheManager shiroCacheManager(CacheManager manager) {
////        EhCacheManager ehCacheManager = new EhCacheManager();
////        ehCacheManager.setCacheManager(manager);
//        return cacheManager;
//    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    //保证实现了Shiro内部lifecycle函数的bean执行
    //添加 static 解决 @Value 为 null 问题
    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    //    AOP式方法级权限检查
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }
}
