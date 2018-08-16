package com.thinkgem.jeesite.modules.m1.web;

import com.thinkgem.jeesite.common.web.*;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vin on 2018/7/3.
 */
@RestController
@RequestMapping(value = "sa")
public class ServiceController extends BaseController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/v2/getAdmin", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户", notes = "数据返回类型json|xml?", produces = "application/json,application/xml")
    public User test() {

        User user = systemService.getUser("1");

        return user;
    }

}
