package com.iams.core.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei yz
 * @ClassName: TeacherVo
 * @Description:
 * @date 2021/2/5 10:52
 */
public class TeacherDto implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 教师号
     */
    private String number;

    /**
     * 职称
     */
    private String rank;

    /**
     * 名称
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
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
     * 创建日期
     */
    private Date createTime;

    @Override
    public String toString() {
        return "TeacherVo{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", rank='" + rank + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", answerInfo='" + answerInfo + '\'' +
                ", institute='" + institute + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAnswerInfo() {
        return answerInfo;
    }

    public void setAnswerInfo(String answerInfo) {
        this.answerInfo = answerInfo;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
