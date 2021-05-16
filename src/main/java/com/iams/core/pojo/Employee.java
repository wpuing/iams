package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.iams.common.validation.Add;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *   员工
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-01
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 员工号
     */
    @NotNull(message = "员工号不能为空！")
    private String number;

    /**
     * 名称
     */
    @NotNull(message = "员工姓名不能为空")
    private String name;

    /**
     * 密码
     */
    @NotNull(message = "员工密码不能为空",groups = Add.class)
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    @NotNull(message = "员工邮箱不能为空")
    @Email
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 随机码
     */
    private String randomId;

    /**
     * 盐
     */
    private String salt;

    /**
     * 角色号
     */
    @Min(value = 1,message = "必须大于等于1")//必须大于等于
    private Integer roleId;

    /**
     * 创建日期
     */
    @TableField(fill = FieldFill.INSERT)//自动创建
    private Date createTime;

    /**
     * 逻辑删除 0未删除 1已删除
     */
    @TableLogic
    @TableField(select = false)//false：查询时不显示该字段，但是查询时会带上这个字段
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public Employee setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getNumber() {
        return number;
    }

    public Employee setNumber(String number) {
        this.number = number;
        return this;
    }
    public String getName() {
        return name;
    }

    public Employee setName(String name) {
        this.name = name;
        return this;
    }
    public String getPassword() {
        return password;
    }

    public Employee setPassword(String password) {
        this.password = password;
        return this;
    }
    public String getSex() {
        return sex;
    }

    public Employee setSex(String sex) {
        this.sex = sex;
        return this;
    }
    public String getEmail() {
        return email;
    }

    public Employee setEmail(String email) {
        this.email = email;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public Employee setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public String getRandomId() {
        return randomId;
    }

    public Employee setRandomId(String randomId) {
        this.randomId = randomId;
        return this;
    }
    public String getSalt() {
        return salt;
    }

    public Employee setSalt(String salt) {
        this.salt = salt;
        return this;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public Employee setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public Employee setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public Employee setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + id +
            ", number=" + number +
            ", name=" + name +
            ", password=" + password +
            ", sex=" + sex +
            ", email=" + email +
            ", phone=" + phone +
            ", randomId=" + randomId +
            ", salt=" + salt +
            ", roleId=" + roleId +
            ", createTime=" + createTime +
            ", deleted=" + deleted +
        "}";
    }
}
