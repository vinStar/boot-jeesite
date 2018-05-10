/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.comEnum.ResultStatus;
import com.thinkgem.jeesite.common.web.ResultModel;
import com.thinkgem.jeesite.common.web.TokenModel;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.security.shiro.session.SessionDAO;
import com.thinkgem.jeesite.common.servlet.ValidateCodeServlet;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.CookieUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.security.FormAuthenticationFilter;
import com.thinkgem.jeesite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录Controller
 *
 * @author ThinkGem
 * @version 2013-5-31
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private SessionDAO sessionDAO;

    /**
     * 登录
     *
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> home() {

        return new ResponseEntity<>(ResultModel.ok("请登录"), HttpStatus.OK);
    }

    /**
     * 管理登录
     */
    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> login(HttpServletRequest request,
                                             HttpServletResponse response, Model model) {
        if (logger.isDebugEnabled()) {
            logger.debug("login, a/login===GET");
        }

        Principal principal = UserUtils.getPrincipal();

        // 默认页签模式
        String tabmode = CookieUtils.getCookie(request, "tabmode");
        if (tabmode == null) {
            CookieUtils.setCookie(response, "tabmode", "1");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("login, active session size: {}",
                    sessionDAO.getActiveSessions(false).size());
        }
        // 如果已经登录，则跳转到管理首页
        if (principal != null && !principal.isMobileLogin()) {

            return new ResponseEntity<>(ResultModel.ok("已登录，返回token" + principal.getSessionid()),
                    HttpStatus.OK);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("is it continues?");
        }
        return new ResponseEntity<>(ResultModel.ok("请登录"), HttpStatus.OK);
    }

    /**
     * 登录失败，真正登录的POST请求由Filter完成
     */
    @RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> loginFail(HttpServletRequest request,
                                                 HttpServletResponse response, Model model) {
        if (logger.isDebugEnabled()) {
            logger.debug("login, a/login===POST");
        }

        Principal principal = UserUtils.getPrincipal();

        // 如果已经登录，则跳转到管理首页
        if (principal != null) {
            return new ResponseEntity<>(
                    ResultModel.ok("已登录，返回token" + principal.getSessionid()), HttpStatus.OK);
        }

        String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
        boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
        boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
        String exception =
                (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
        String message = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

        if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")) {
            message = "用户或密码错误, 请重试.";
        }

        model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
        model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

        if (logger.isDebugEnabled()) {
            logger.debug("login fail, active session size: {}, message: {}, exception: {}",
                    sessionDAO.getActiveSessions(false).size(), message, exception);
        }

        // 非授权异常，登录失败，验证码加1。
        if (!UnauthorizedException.class.getName().equals(exception)) {
            model.addAttribute("isValidateCodeLogin",
                    isValidateCodeLogin(username, true, false));
        }

        // 验证失败清空验证码
        request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

        // 如果是手机登录，则返回JSON字符串
//        if (mobile) {
//            return renderString(response, model);
//        }

        return new ResponseEntity<>(ResultModel.ok(message), HttpStatus.OK);
    }

    /**
     * 登录成功，进入管理首页
     */
    @RequiresPermissions("user")
    @RequestMapping(value = "${adminPath}")
    public ResponseEntity<ResultModel> index(HttpServletRequest request, HttpServletResponse response) {
        if (logger.isDebugEnabled()) {
            logger.debug("/a====");
        }

        Principal principal = UserUtils.getPrincipal();

        // 登录成功后，验证码计算器清零
        isValidateCodeLogin(principal.getLoginName(), false, true);

        if (logger.isDebugEnabled()) {
            logger.debug("show index, active session size: {}",
                    sessionDAO.getActiveSessions(false).size());
        }

        // 登录成功返回 token
        if (principal != null) {
            TokenModel tokenModel=new TokenModel(principal.getLoginName(),principal.getSessionid());

            return new ResponseEntity<>(ResultModel.ok(tokenModel),
                    HttpStatus.OK);
        } else {
            //失败
            return new ResponseEntity<>(ResultModel.error(ResultStatus.FAIL_UNKNOWN_REASON),
                    HttpStatus.OK);
        }
    }

    /**
     * 获取主题方案
     */
    @RequestMapping(value = "/theme/{theme}")
    public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request,
                                   HttpServletResponse response) {
        if (StringUtils.isNotBlank(theme)) {
            CookieUtils.setCookie(response, "theme", theme);
        } else {
            theme = CookieUtils.getCookie(request, "theme");
        }
        return "redirect:" + request.getParameter("url");
    }

    /**
     * 是否是验证码登录
     *
     * @param useruame 用户名
     * @param isFail   计数加1
     * @param clean    计数清零
     * @return
     */
    @SuppressWarnings("unchecked")
    public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean) {
        Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
        if (loginFailMap == null) {
            loginFailMap = Maps.newHashMap();
            CacheUtils.put("loginFailMap", loginFailMap);
        }
        Integer loginFailNum = loginFailMap.get(useruame);
        if (loginFailNum == null) {
            loginFailNum = 0;
        }
        if (isFail) {
            loginFailNum++;
            loginFailMap.put(useruame, loginFailNum);
        }
        if (clean) {
            loginFailMap.remove(useruame);
        }
        return loginFailNum >= 3;
    }
}
