package com.thinkgem.jeesite.modules.m3.web;

import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.ResultModel;
import com.thinkgem.jeesite.modules.m3.service.UserService;
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
@RequestMapping(value = "sb")
public class ServiceController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/v2/getAdmin", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户", notes = "数据返回类型json|xml?", produces = "application/json,application/xml")
    public ResponseEntity<ResultModel> test() {

        User user = userService.getUser();
        return new ResponseEntity<>(ResultModel.ok(user), HttpStatus.OK);

    }

}
