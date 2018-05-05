package com.thinkgem.jeesite.modules.other.homeController;

import com.thinkgem.jeesite.common.datasource.DynamicDataSourceContextHolder;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vin on 10/04/2018.
 */
@RestController
@RequestMapping(value = "home/")
public class HomeController extends BaseController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/v2/test", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户", notes = "数据返回类型json|xml?", produces = "application/xml,application/json")
    public User test() {

        User user = systemService.getUser("1");

        return user;
    }

    @RequestMapping(value = "/v2/test2", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户", notes = "数据返回类型json|xml?", produces = "application/xml,application/json")
    public User test2() {

        DynamicDataSourceContextHolder.setDateSoureType("db2");

        User user2 = systemService.getUser("2");
        DynamicDataSourceContextHolder.clearDateSoureType();
        return user2;

    }


    @RequestMapping(value = "/v2/hello", method = RequestMethod.GET)
    @ApiOperation(value = "say hi", notes = "数据返回类型json|xml?", produces = "application/xml,application/json")
    public String hello() {

        return "hello world";
    }


}
