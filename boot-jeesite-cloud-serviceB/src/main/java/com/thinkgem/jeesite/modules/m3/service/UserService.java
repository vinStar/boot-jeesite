package com.thinkgem.jeesite.modules.m3.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.thinkgem.jeesite.common.web.ResultModel;
import com.thinkgem.jeesite.modules.sys.entity.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


/**
 * Created by vin on 2018/8/9.
 */
@Service
public class UserService {

    @Autowired
    RestTemplate restTemplate;

    final String SERVICE_A_NAME = "cloud-service-A";


    // 降级
    @HystrixCommand(fallbackMethod = "fallbackGetUser")
    @ApiOperation(value = "获取用户", notes = "数据返回类型json|xml?", produces = "application/json,application/xml")
    public  User getUser() {

        //String aa = restTemplate.getForEntity("http://" + SERVICE_A_NAME + "/sa/v2/getAdmin", String.class);
        ResponseEntity<User> responseEntity = restTemplate.getForEntity("http://" + SERVICE_A_NAME + "/sa/v2/getAdmin", User.class);

        return responseEntity.getBody();
    }

    /**
     * Return stubbed fallback with some static defaults, placeholders,
     * and an injected value 'countryCodeFromGeoLookup' that we'll use
     * instead of what we would have retrieved from the remote service.
     */
    private ResponseEntity<ResultModel> fallbackGetUser() {
        System.out.println("HystrixCommand fallbackMethod handle!");

        User user = new User();
        user.setLoginName("fallback TestHystrixCommand");

        return new ResponseEntity<>(ResultModel.ok(user), HttpStatus.OK);
    }
}
