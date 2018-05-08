package com.thinkgem.jeesite.modules.other.helloController;

import com.thinkgem.jeesite.common.datasource.DynamicDataSourceContextHolder;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.web.ResultModel;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by vin on 10/04/2018.
 */
@RestController
@Api(value = "Hello", description = "测试HelloController")
public class HelloController extends BaseController {

    @Autowired
    private SystemService systemService;

    //支持xml,json 设置 produces = "application/xml,application/json")
    @ApiOperation(value = "获取用户", notes = "数据返回类型json|xml?", produces = "application/xml,application/json")
    @RequestMapping(value = "/v0.1/test", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> test() {

        User user = systemService.getUser("1");

        return new ResponseEntity<>(ResultModel.ok(user), HttpStatus.OK);
    }

    @ApiOperation(value = "获取用户", notes = "数据返回类型json|xml?", produces = "application/xml,application/json")
    @RequestMapping(value = "/v0.1/test2", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> test2() {

        DynamicDataSourceContextHolder.setDateSoureType("db2");

        User user2 = systemService.getUser("2");
        DynamicDataSourceContextHolder.clearDateSoureType();
        return new ResponseEntity<>(ResultModel.ok(user2), HttpStatus.OK);

    }


    @RequestMapping(value = "/v0.1/hello", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> hello() {

        String hi = "hello world";
        return new ResponseEntity<>(ResultModel.ok(hi), HttpStatus.OK);
    }


}
