# boot-jeesite
开源框架JeeSite集成SpringBoot实例

## boot-jeesite

 本项目衍生自 
 [jeesite](https://github.com/thinkgem/jeesite)、
 [开源框架JeeSite集成SpringBoot实例](https://www.cnblogs.com/frightOAO/p/7857743.html)
 参考
 [one](https://github.com/lcw2004/one)
 [renren-fast](https://gitee.com/babaio/renren-fast)
 [L316476844/springbootexample](https://github.com/L316476844/springbootexample)
 [Foreveriss/SpringBoot](https://github.com/Foreveriss/SpringBoot)
 
 在sb实例的基础上，添加了原有的内容管理（CMS）、模块代码生成（GEN）模块
 
## 技术栈
- 后端
    -   Spring Boot 1.5.6.RELEASE
    -   使用SSM框架, Spring+Spring MVC+MyBatis3.2.8

- 前端
    -   xxx
    
        
## 启动
- jsp 开发模式（含代码生成， cms），需配置 tomcat 启动，打成 war 包，tomcat 部署。   
    - http://localhost:8087   登录页    
- 前后端模式后端，可去除支持 jsp 的相关支持，springboot 启动类启动，打成 jar 包部署。   
    - http://localhost:8087/test/  返回测试api数据
    
## 2018-05-08  
1. 添加 api 返回标准格式   
2. 添加全局异常处理  
3. 删除 supcan，static  
    
## 2018-05-05

+ f, 
1. bean 配置 ehCache 
2. 修改默认数据库 url 
3. 添加单元测试
4. 修复启动单元测试或者 `maven test` shiroCache cacheManger 注入多个
5. 添加 swagger api 接口文档
6. 配置类全局跨域
7. shiro filter 暂无权限限制
    
## 2018-05-02

0. 支持多数据源（spring boot+druid+mybatis+多数据源），重点参考2     
1. 没有采用 aop 切换数据源。人为再 controller 层控制数据源选择。    
2. 没有采用简单负载，只是针对不同数据源非主从模式，determineCurrentLookupKey 默认 db1 数据源  
[参考1](http://www.cnblogs.com/yjmyzz/p/spring-boot-integrate-with-mybatis-and-multi-datasource.html)  
[参考2](https://github.com/drtrang/druid-spring-boot)  
       
       
## 2018-04-27  
0.解决打成的 jar 包过大问题，便于后期部署或者 docker 集成  
1.maven plugin 设置 jar 分离 lib 'thin jar'  
[refer](https://my.oschina.net/xiaozhutefannao/blog/1624092)  

   
## 2018-04-11
   
0. 修改启动后自动访问 '/'( 去除 yml 内 springMVC index 配置项 , 非 sb 项目在 springMVC 配置文件)    
1. 配置 druid
2. 日志文件路径 定位到项目路径内 logs ehcache
3. modules 添加 一个简单测试 controller
    
## to-do-list
