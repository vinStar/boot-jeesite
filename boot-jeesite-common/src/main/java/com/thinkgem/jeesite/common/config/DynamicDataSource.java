package com.thinkgem.jeesite.common.config;

import com.thinkgem.jeesite.common.datasource.DynamicDataSourceContextHolder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vin on 02/05/2018.
 */
@Slf4j
@Getter
@RequiredArgsConstructor
public class DynamicDataSource extends AbstractRoutingDataSource {

    private final AtomicInteger count = new AtomicInteger();
    private final Map<String, DataSource> targetDataSources;

    /**
     * 基于轮询算法指定实际的数据源
     *
     * @return dataSource
     */
    @Override
    protected Object determineCurrentLookupKey() {
//        int i = count.incrementAndGet();
//        List<String> list = new ArrayList<>(targetDataSources.keySet());
//        String dataSource = list.get(i % list.size());
//        String info=">>>>>> 当前数 据库: {"+ dataSource +"} <<<<<<";
//        System.out.println(info);
//        return dataSource;
        String dataSource = DynamicDataSourceContextHolder.getDateSoureType();
        dataSource = dataSource == null ? "db1" : dataSource;
        log.info(">>>>>> 当前数据库: {" + dataSource + "} <<<<<<");
        return dataSource;
    }

    @Override
    public void afterPropertiesSet() {
        super.setTargetDataSources(new HashMap<>(targetDataSources));
        super.afterPropertiesSet();
    }

}