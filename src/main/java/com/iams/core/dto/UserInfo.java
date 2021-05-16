package com.iams.core.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: UserInfo
 * @Description:
 * @date 2021/4/23 18:40
 */
public class UserInfo implements Serializable {

    /**
     * 用户名
     */
    @NotNull(message = "登录账号不能为空！")
    private String username;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空！")
    private String password;

    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空！")
    private String captcha;

    /**
     * 盐
     */
    private String salt;

    /**
     * 角色
     */
    @NotNull(message = "用户角色不能为空！")
    private String role;

    public String getUsername() {
        return username;
    }

    public UserInfo setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCaptcha() {
        return captcha;
    }

    public UserInfo setCaptcha(String captcha) {
        this.captcha = captcha;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRole() {
        return role;
    }

    public UserInfo setRole(String role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", captcha='" + captcha + '\'' +
                ", salt='" + salt + '\'' +
                ", role=" + role +
                '}';
    }
}
