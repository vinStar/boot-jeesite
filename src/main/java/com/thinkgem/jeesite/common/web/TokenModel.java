package com.thinkgem.jeesite.common.web;

/**
 * @Description: Token的Model类， 可以增加字段提高安全性，例如时间戳、url签名
 * @Author: gre_yu@163.com
 * @Date: Created in 0:57 2018/2/1
 */
public class TokenModel {

    private String userId;

    // 随机生成的uuid
    private String token;

    public TokenModel(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                '}';
    }
}
