package com.thinkgem.jeesite.modules.sys.security;

/**
 * Created by vin on 2018/5/15.
 */

import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import java.io.Serializable;

/**
 * 授权用户信息
 */
public class Principal implements Serializable {


    private static final long serialVersionUID = 1L;

    private String id; // 编号
    private String loginName; // 登录名
    private String name; // 姓名
    private boolean mobileLogin; // 是否手机登录

//		private Map<String, Object> cacheMap;

    public Principal(User user, boolean mobileLogin) {

        this.id = user.getId();
        this.loginName = user.getLoginName();
        this.name = user.getName();
        this.mobileLogin = mobileLogin;
    }

    public String getId() {
        return id;
    }

    public String getLoginName() {
        return loginName;
    }

    public String getName() {
        return name;
    }

    public boolean isMobileLogin() {
        return mobileLogin;
    }

//		@JsonIgnore
//		public Map<String, Object> getCacheMap() {
//			if (cacheMap==null){
//				cacheMap = new HashMap<String, Object>();
//			}
//			return cacheMap;
//		}

    /**
     * 获取SESSIONID
     */
    public String getSessionid() {
        try {
            return (String) UserUtils.getSession().getId();
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String toString() {
        return id;
    }

}
