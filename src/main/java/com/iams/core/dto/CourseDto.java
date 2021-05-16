package com.iams.core.dto;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: CourseDto
 * @Description:  管理员的课程管理列表类
 * @date 2021/3/4 13:27
 */
public class CourseDto implements Serializable {

    /**
     *  课程id
     */
    private Integer courseId;

    /**
     *  课程编号
     */
    private String courseNumber;

    /**
     *  课程名
     */
    private String courseName;

    /**
     *  授课id，课程教师关系id
     */
    private Integer giveId;

    /**
     *  教师编号
     */
    private String teacherNumber;

    /**
     *  教师名
     */
    private String teacherName;

    public Integer getCourseId() {
        return courseId;
    }

    public CourseDto setCourseId(Integer courseId) {
        this.courseId = courseId;
        return this;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public CourseDto setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
        return this;
    }

    public String getCourseName() {
        return courseName;
    }

    public CourseDto setCourseName(String courseName) {
        this.courseName = courseName;
        return this;
    }

    public Integer getGiveId() {
        return giveId;
    }

    public CourseDto setGiveId(Integer giveId) {
        this.giveId = giveId;
        return this;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public CourseDto setTeacherNumber(String teacherNumber) {
        this.teacherNumber = teacherNumber;
        return this;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public CourseDto setTeacherName(String teacherName) {
        this.teacherName = teacherName;
        return this;
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "courseId=" + courseId +
                ", courseNumber='" + courseNumber + '\'' +
                ", courseName='" + courseName + '\'' +
                ", giveId=" + giveId +
                ", teacherNumber='" + teacherNumber + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
