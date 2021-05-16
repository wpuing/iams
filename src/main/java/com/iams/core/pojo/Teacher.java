package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *   教师
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 教师号
     */
    @NotNull(message = "教师号不能为空！")
    private String number;

    /**
     * 职称
     */
    private String rank;

    /**
     * 名称
     */
    @NotNull(message = "教师姓名不能为空")
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    @NotNull(message = "教师邮箱不能为空")
    @Email
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 答疑信息
     */
    private String answerInfo;

    /**
     * 学院
     */
    private String institute;

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

    public Teacher setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getNumber() {
        return number;
    }

    public Teacher setNumber(String number) {
        this.number = number;
        return this;
    }
    public String getRank() {
        return rank;
    }

    public Teacher setRank(String rank) {
        this.rank = rank;
        return this;
    }
    public String getName() {
        return name;
    }

    public Teacher setName(String name) {
        this.name = name;
        return this;
    }
    public String getPassword() {
        return password;
    }

    public Teacher setPassword(String password) {
        this.password = password;
        return this;
    }
    public String getSex() {
        return sex;
    }

    public Teacher setSex(String sex) {
        this.sex = sex;
        return this;
    }
    public String getEmail() {
        return email;
    }

    public Teacher setEmail(String email) {
        this.email = email;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public Teacher setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public String getAnswerInfo() {
        return answerInfo;
    }

    public Teacher setAnswerInfo(String answerInfo) {
        this.answerInfo = answerInfo;
        return this;
    }
    public String getInstitute() {
        return institute;
    }

    public Teacher setInstitute(String institute) {
        this.institute = institute;
        return this;
    }
    public String getRandomId() {
        return randomId;
    }

    public Teacher setRandomId(String randomId) {
        this.randomId = randomId;
        return this;
    }
    public String getSalt() {
        return salt;
    }

    public Teacher setSalt(String salt) {
        this.salt = salt;
        return this;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public Teacher setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public Teacher setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public Teacher setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "id=" + id +
            ", number=" + number +
            ", rank=" + rank +
            ", name=" + name +
            ", password=" + password +
            ", sex=" + sex +
            ", email=" + email +
            ", phone=" + phone +
            ", answerInfo=" + answerInfo +
            ", institute=" + institute +
            ", randomId=" + randomId +
            ", salt=" + salt +
            ", roleId=" + roleId +
            ", createTime=" + createTime +
            ", deleted=" + deleted +
        "}";
    }
}
