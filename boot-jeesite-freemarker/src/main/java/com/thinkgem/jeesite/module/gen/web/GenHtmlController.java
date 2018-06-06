package com.thinkgem.jeesite.module.gen.web;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.thinkgem.jeesite.common.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by vin on 2018/5/28.
 */
@CrossOrigin(origins = "*/*", maxAge = 3600)
@RestController
public class GenHtmlController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(GenHtmlController.class);

    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
    //多线程生成静态页面
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 10l, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(1000));

    @Autowired
    public Configuration configuration;

    @Value("${staticPath}")
    private String path;


    @CrossOrigin("*")
    @RequestMapping(value = "/v0.1/freeMarker", method = RequestMethod.GET)
    public String sendHtmlMailUsingFreeMarker(HttpServletRequest request) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("time", new Date());
        model.put("message", "这是测试的内容。。。");
        model.put("toUserName", "张三");
        model.put("fromUserName", "老许");


        Template t = configuration.getTemplate("welcome.ftl"); // freeMarker template
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

//        String pathDir = System.getProperty("user.dir");
//        String pathFile = pathDir + "/boot-jeesite-freemarker/staticHtml/" + "genStatic" + ".html";

        File file = new File(path + "genStatic" + ".html");
        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        t.process(model, writer);

        logger.debug(content);
        return content;
        //mailService.sendHtmlMail(to, "主题：html邮件", content);
    }

}
