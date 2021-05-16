package com.iams.core.dto;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: CourseStudentDto
 * @Description: 课程学生类
 * @date 2021/3/4 16:51
 */
public class CourseStudentDto implements Serializable {

    /**
     * 课程学生关系id
     */
    private Integer giveId;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 学生姓名
     */
    private String studentName;

    public Integer getGiveId() {
        return giveId;
    }

    public CourseStudentDto setGiveId(Integer giveId) {
        this.giveId = giveId;
        return this;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public CourseStudentDto setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
        return this;
    }

    public String getStudentName() {
        return studentName;
    }

    public CourseStudentDto setStudentName(String studentName) {
        this.studentName = studentName;
        return this;
    }

    @Override
    public String toString() {
        return "CourseStudentDto{" +
                "giveId=" + giveId +
                ", studentNumber='" + studentNumber + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
