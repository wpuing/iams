package com.iams.core.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: PersonalInfo
 * @Description: 注册的个人信息
 * @date 2021/4/24 23:45
 */
public class PersonalInfo implements Serializable {

    /**
     * 编号
     */
    @NotNull(message = "编号不能为空！")
    private String number;

    /**
     * 名称
     */
    @NotNull(message = "姓名不能为空")
    private String name;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;

    /**
     * 邮箱
     */
    @NotNull(message = "邮箱不能为空")
    private String email;

    /**
     * 角色号
     */
    @NotNull(message = "角色不能为空")
    private Integer roleId;

    public String getNumber() {
        return number;
    }

    public PersonalInfo setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public PersonalInfo setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public PersonalInfo setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PersonalInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public PersonalInfo setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roleId=" + roleId +
                '}';
    }
}
