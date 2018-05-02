package com.thinkgem.jeesite.modules.other.controller;

import com.thinkgem.jeesite.common.datasource.DynamicDataSourceContextHolder;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vin on 10/04/2018.
 */
@RestController
public class testController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public User test() {

//        List<String> testList = new ArrayList<String>();
//        testList.add("aa");
//        testList.add("bb");
//        testList.add("bb");
//
//        User user = new User();
//        user.setName("Grace");
//        user.setTestList(testList);


        return systemService.getUser("1");
    }

    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public User test2() {

        DynamicDataSourceContextHolder.setDateSoureType("db2");

        User user2 = systemService.getUser("2");
        DynamicDataSourceContextHolder.clearDateSoureType();
        return user2;

    }


}
