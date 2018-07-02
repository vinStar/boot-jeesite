package com.thinkgem.jeesite.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by vin on 05/05/2018.
 */
@Configuration //让Spring来加载该类配置
@EnableSwagger2 //启用Swagger2
public class Swagger2 {
    @Bean
    public Docket helloApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("hello-API接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.thinkgem.jeesite.modules.other.helloController"))
                .paths(PathSelectors.any()).build();
    }

    @Bean
    public Docket homeApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("home-API接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.thinkgem.jeesite.modules.other.homeController"))
                .paths(PathSelectors.any()).build();
    }

    @Bean
    public Docket ossApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("oss-API接口文档")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.thinkgem.jeesite.modules.oss"))
                .paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("xx系统")
                .description("helloService、homeService")
                .termsOfServiceUrl("http://github.com")
                .contact(new Contact("github ", "http://github.com", "xxx@qq.com"))
                .version("1.0").build();
    }

}
