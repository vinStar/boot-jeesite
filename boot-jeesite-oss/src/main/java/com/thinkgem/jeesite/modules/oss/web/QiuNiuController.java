package com.thinkgem.jeesite.modules.oss.web;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.thinkgem.jeesite.common.web.ResultModel;
import com.thinkgem.jeesite.modules.oss.config.ConstantQiniu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by vin on 2018/7/2.
 */

@Api("qiniu OSS")
@RestController
@RequestMapping("/qiniu/upload")
public class QiuNiuController {

    @Autowired
    private ConstantQiniu constantQiniu;

    @ApiOperation(
            value = "获取上传属性",
            notes = "qi niu notes",
            produces = "application/json")
    @RequestMapping(value = "value", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getProperties() {

        return new ResponseEntity<>(ResultModel.ok(constantQiniu), HttpStatus.OK);
    }


    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String uploadImg(HttpServletRequest request) throws Exception {
        String filePath = "";
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();

            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                String fileName = file.getOriginalFilename();
                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
                DefaultPutRet putRet = upload(file.getBytes(), newFileName);
                filePath += constantQiniu.getBucketUrl() + putRet.key + ",";
            }
            if (filePath.endsWith(",")) {
                filePath = filePath.substring(0, filePath.length() - 1);
            }
        }
        return filePath;
    }


    public DefaultPutRet upload(byte[] file, String key) throws Exception {
        Auth auth = Auth.create(constantQiniu.getAccessKey(), constantQiniu.getSecretKey());
        //第二种方式: 自动识别要上传的空间(bucket)的存储区域是华东、华北、华南。
        Zone z = Zone.autoZone();
        Configuration c = new Configuration(z);
        //创建上传对象
        UploadManager uploadManager = new UploadManager(c);
        Response res = uploadManager.put(file, key, getUpToken(auth, constantQiniu.getBucket()));
        //打印返回的信息
        System.out.println(res.bodyString());
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(res.bodyString(), DefaultPutRet.class);
        return putRet;
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(Auth auth, String bucketname) {
        return auth.uploadToken(bucketname);
    }

}
