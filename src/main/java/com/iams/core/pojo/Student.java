package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *   学生
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学号
     */
    @NotNull(message = "学号不能为空！")
    private String number;

    /**
     * 名称
     */
    @NotNull(message = "学生姓名不能为空")
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 年龄
     */
    private Integer age;

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
     * 电话
     */
    private String phone;

    /**
     * 专业
     */
    private String professional;

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
     * 创建时间
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

    public Student setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getNumber() {
        return number;
    }

    public Student setNumber(String number) {
        this.number = number;
        return this;
    }
    public String getName() {
        return name;
    }

    public Student setName(String name) {
        this.name = name;
        return this;
    }
    public String getPassword() {
        return password;
    }

    public Student setPassword(String password) {
        this.password = password;
        return this;
    }
    public Integer getAge() {
        return age;
    }

    public Student setAge(Integer age) {
        this.age = age;
        return this;
    }
    public String getSex() {
        return sex;
    }

    public Student setSex(String sex) {
        this.sex = sex;
        return this;
    }
    public String getEmail() {
        return email;
    }

    public Student setEmail(String email) {
        this.email = email;
        return this;
    }
    public String getPhone() {
        return phone;
    }

    public Student setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public String getProfessional() {
        return professional;
    }

    public Student setProfessional(String professional) {
        this.professional = professional;
        return this;
    }
    public String getInstitute() {
        return institute;
    }

    public Student setInstitute(String institute) {
        this.institute = institute;
        return this;
    }
    public String getRandomId() {
        return randomId;
    }

    public Student setRandomId(String randomId) {
        this.randomId = randomId;
        return this;
    }
    public String getSalt() {
        return salt;
    }

    public Student setSalt(String salt) {
        this.salt = salt;
        return this;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public Student setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public Student setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public Student setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        return "Student{" +
            "id=" + id +
            ", number=" + number +
            ", name=" + name +
            ", password=" + password +
            ", age=" + age +
            ", sex=" + sex +
            ", email=" + email +
            ", phone=" + phone +
            ", professional=" + professional +
            ", institute=" + institute +
            ", randomId=" + randomId +
            ", salt=" + salt +
            ", roleId=" + roleId +
            ", createTime=" + createTime +
            ", deleted=" + deleted +
        "}";
    }
}
