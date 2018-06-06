//package com.thinkgem.jeesite.modules.other.helloController;
//
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.OutputStreamWriter;
//import java.io.Writer;
//import java.util.*;
//import java.util.concurrent.Callable;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.Future;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//import com.thinkgem.jeesite.common.controller.BaseController;
//import com.thinkgem.jeesite.modules.sys.interceptor.LogThread;
//import io.swagger.annotations.Api;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import freemarker.template.Configuration;
//import freemarker.template.Template;
//import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
//import org.springframework.controller.bind.annotation.RequestMapping;
//import org.springframework.controller.bind.annotation.RequestMethod;
//import org.springframework.controller.bind.annotation.RestController;
//
///**
// * Created by vin on 2018/5/28.
// */
//@RestController
//public class aa extends BaseController {
//    private static Logger logger = LoggerFactory.getLogger(aa.class);
//
//    private static int corePoolSize = Runtime.getRuntime().availableProcessors();
//    //多线程生成静态页面
//    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, corePoolSize + 1, 10l, TimeUnit.SECONDS,
//            new LinkedBlockingQueue<Runnable>(1000));
//
//    @Autowired
//    public Configuration configuration;
//
//    @Value("${spring.freemarker.html.path}")
//    private String path;
//
//
//    @RequestMapping(value = "/v0.1/freeMarker", method = RequestMethod.GET)
//    public String sendHtmlMailUsingFreeMarker() throws Exception {
//        Map<String, Object> model = new HashMap<String, Object>();
//        model.put("time", new Date());
//        model.put("message", "这是测试的内容。。。");
//        model.put("toUserName", "张三");
//        model.put("fromUserName", "老许");
//
//        Template t = configuration.getTemplate("welcome.ftl"); // freeMarker template
//        String content = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
//
//        File file = new File(path + "aa" + ".html");
//        Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
//        t.process(model, writer);
//
//        logger.debug(content);
//        return content;
//        //mailService.sendHtmlMail(to, "主题：html邮件", content);
//    }
//
////    public String createAllHtml() {
////
////        final List<Future<String>> resultList = new ArrayList<Future<String>>();
////        Object object = new Object();
////        executor.submit(new createhtml(object));
////
////        for (Future<String> fs : resultList) {
////            try {
////                System.out.println(fs.get());//打印各个线任务执行的结果，调用future.get() 阻塞主线程，获取异步任务的返回结果
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } catch (ExecutionException e) {
////                e.printStackTrace();
////            }
////        }
////        return "ok";
////    }
////
////    class createhtml implements Callable<String> {
////        Object seckill;
////
////        public createhtml(Object seckill) {
////            this.seckill = seckill;
////        }
////
////        @Override
////        public String call() throws Exception {
////            Template template = configuration.getTemplate("welcome.flt");
////            File file = new File(path + "aa" + ".html");
////            Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
////            template.process(seckill, writer);
////            return "success";
////        }
////    }
//}
