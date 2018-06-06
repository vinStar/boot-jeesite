/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.utils;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Log;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.interceptor.InterceptorLogEntity;
import com.thinkgem.jeesite.modules.sys.interceptor.LogThread;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 字典工具类
 *
 * @author ThinkGem
 * @version 2014-11-7
 */
public class LogUtils {

    public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, String title) {
        saveLog(request, null, null, title);
    }

    /**
     * 保存日志
     */
    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title) {
        User user = UserUtils.getUser();
        if (user != null && user.getId() != null) {
            Log log = new Log();
            log.setTitle(title);
            log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
            log.setRemoteAddr(StringUtils.getRemoteAddr(request));
            log.setUserAgent(request.getHeader("user-agent"));
            log.setRequestUri(request.getRequestURI());
            log.setParams(request.getParameterMap());
            log.setMethod(request.getMethod());
            log.setCreateBy(user);
            log.setUpdateBy(user);
            log.setUpdateDate(new Date());
            log.setCreateDate(new Date());
            // 异步保存日志
            try {
                InterceptorLogEntity entiry = new InterceptorLogEntity(log, handler, ex);
                LogThread.interceptorLogQueue.put(entiry);
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
