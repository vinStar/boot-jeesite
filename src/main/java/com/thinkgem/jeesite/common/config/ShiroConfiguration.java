package com.thinkgem.jeesite.common.config;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.security.shiro.session.CacheSessionDAO;
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
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
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
public class ShiroConfiguration {

    /**
     * 全局的环境变量的设置
     * shiro的拦截
     *
     * @param environment
     * @param adminPath
     * @return
     */
//     Shiro权限过滤过滤器定义
    @Bean(name = "shiroFilterChainDefinitions")
    public LinkedHashMap<String, String> shiroFilterChainDefinitions(Environment environment, @Value("${adminPath}") String adminPath) {
        Global.resolver = new RelaxedPropertyResolver(environment);
        // 配置访问权限
        LinkedHashMap<String, String> linkedHashMap = Maps.newLinkedHashMap();
        linkedHashMap.put("/sso/**", "anon");
        linkedHashMap.put("/static/**", "anon");
        linkedHashMap.put("/Scripts/**", "anon");
        linkedHashMap.put("/userfiles/**", "anon");
//        linkedHashMap.put(adminPath + "/cas", "cas");
        linkedHashMap.put(adminPath + "/login", "anon");
        linkedHashMap.put(adminPath + "/logout", "logout");
        linkedHashMap.put("modules/sys/sysLogin", "anon");
//        linkedHashMap.put("/a/login", "anon");
        linkedHashMap.put(adminPath + "/**", "user");
//        linkedHashMap.put("/act/editor/**", "user");
//        linkedHashMap.put("/ReportServer/**", "user");
        return linkedHashMap;
    }

//    @Bean(name = "basicHttpAuthenticationFilter")
//    public BasicHttpAuthenticationFilter casFilter(@Value("${adminPath}") String adminPath) {
//        BasicHttpAuthenticationFilter basicHttpAuthenticationFilter = new BasicHttpAuthenticationFilter();
//        basicHttpAuthenticationFilter.setLoginUrl(adminPath + "/login");
//        return basicHttpAuthenticationFilter;
//    }

    //    安全认证过滤器
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(
            @Value("${adminPath:/a}") String adminPath,
//            BasicHttpAuthenticationFilter basicHttpAuthenticationFilter,
            FormAuthenticationFilter formAuthenticationFilter,
            DefaultWebSecurityManager securityManager,
            @Qualifier("shiroFilterChainDefinitions") LinkedHashMap<String, String> shiroFilterChainDefinitions) {
        Map<String, Filter> filters = new HashMap<>();
//        filters.put("basic", basicHttpAuthenticationFilter);
        filters.put("authc", formAuthenticationFilter);
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setFilters(filters);
        bean.setSecurityManager(securityManager);
        bean.setLoginUrl(adminPath + "/login");
        bean.setSuccessUrl(adminPath + "?login");
        bean.setFilterChainDefinitionMap(shiroFilterChainDefinitions);
        return bean;
    }

    @Bean(name = "shiroCacheManager")
    public EhCacheManager shiroCacheManager(CacheManager manager) {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(manager);
        return ehCacheManager;
    }

    //    自定义会话管理配置
    @Bean(name = "sessionManager")
    public SessionManager sessionManager(
            CacheSessionDAO dao,
            @Value("${session.sessionTimeout}") Long sessionTimeout,
            @Value("${session.sessionTimeoutClean}") Long sessionValidationInterval,
            @Value("${session.simpleCookie}") String simpleCookie) {
        SessionManager sessionManager = new SessionManager();
        sessionManager.setSessionDAO(dao);
        sessionManager.setGlobalSessionTimeout(sessionTimeout);
        sessionManager.setSessionValidationInterval(sessionValidationInterval);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookie(new SimpleCookie(simpleCookie));
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    //    定义Shiro安全管理配置
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(
            SystemAuthorizingRealm systemAuthorizingRealm,
            SessionManager sessionManager,
            EhCacheManager ehCacheManager) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setSessionManager(sessionManager);
        defaultWebSecurityManager.setCacheManager(ehCacheManager);
        defaultWebSecurityManager.setRealm(systemAuthorizingRealm);
        return defaultWebSecurityManager;
    }

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

    //    保证实现了Shiro内部lifecycle函数的bean执行
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
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
