package com.thinkgem.jeesite.common.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;


/**
 * MyBaits配置类
 * <p>Title: MapperScannerConfiguration</p>
 * <p>Description: </p>
 * @author	OAO
 * @date	2017-11-09
 * @version 1.0
 */
@Component
public class MapperScannerConfiguration {

	/**
	 * 扫描basePackage下所有以@MyBatisDao注解的接口
	 * @return
	 */
    @Bean(name = "mapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.thinkgem.jeesite");
        mapperScannerConfigurer.setAnnotationClass(MyBatisDao.class);
        return mapperScannerConfigurer;
    }
    
}
