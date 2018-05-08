package com.thinkgem.jeesite.modules.other.homeController;

import com.thinkgem.jeesite.common.comEnum.ResultStatus;
import com.thinkgem.jeesite.common.datasource.DynamicDataSourceContextHolder;
import com.thinkgem.jeesite.common.web.BaseController;

import com.thinkgem.jeesite.common.web.ResultModel;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vin on 10/04/2018.
 */
@RestController
@RequestMapping(value = "home/")
public class HomeController extends BaseController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/v2/test", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户", notes = "数据返回类型json|xml?", produces = "application/json,application/xml")
    public ResponseEntity<ResultModel> test() {

        User user = systemService.getUser("1");

        return new ResponseEntity<>(ResultModel.ok(user), HttpStatus.OK);
    }

    @RequestMapping(value = "/v2/test2", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户", notes = "数据返回类型json|xml?", produces = "application/json,application/xml")
    public ResponseEntity<ResultModel> test2() {

        DynamicDataSourceContextHolder.setDateSoureType("db2");

        User user2 = systemService.getUser("2");
        DynamicDataSourceContextHolder.clearDateSoureType();
        return new ResponseEntity<>(ResultModel.ok(user2), HttpStatus.OK);

    }


    @RequestMapping(value = "/v2/hello", method = RequestMethod.GET)
    @ApiOperation(value = "say hi", notes = "数据返回类型json|xml?", produces = "application/json,application/xml")
    public ResponseEntity<ResultModel> hello() {
        String hi = "hello world";
        return new ResponseEntity<>(ResultModel.ok(hi), HttpStatus.OK);
    }

    @RequestMapping(value = "/v2/warn", method = RequestMethod.GET)
    @ApiOperation(value = "say warn", notes = "数据返回类型json|xml?", produces = "application/json,application/xml")
    public ResponseEntity<ResultModel> warn() {
        return new ResponseEntity<>(ResultModel.ok(ResultStatus.DATA_NOT_NULL), HttpStatus.OK);
    }

    /**
     * 随机抛出异常.
     */
    private void randomException() throws Exception {
        Exception[] exceptions = { //异常集合
                new NullPointerException(),
                new ArrayIndexOutOfBoundsException(),
                new NumberFormatException(),
                new SQLException()};
        //发生概率
        double probability = 0.75;
        if (Math.random() < probability) {
            //情况1：要么抛出异常
            throw exceptions[(int) (Math.random() * exceptions.length)];
        } else {
            //情况2：要么继续运行
        }

    }

    /**
     * 模拟用户数据访问.
     */
    @RequestMapping(value = "/v2/error", method = RequestMethod.GET)
    @ApiOperation(value = "say error", notes = "数据返回类型json|xml?", produces = "application/json,application/xml")
    public ResponseEntity<ResultModel> error() throws Exception {
        randomException();
        List list = Arrays.asList("正常用户数据1!", "正常用户数据2! 请按F5刷新!!");

        return new ResponseEntity<>(ResultModel.ok(list), HttpStatus.OK);
    }


}
