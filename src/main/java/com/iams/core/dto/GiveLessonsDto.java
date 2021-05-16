package com.iams.core.dto;

import javax.validation.constraints.NotNull;

/**
 * @author Wei yz
 * @ClassName: GiveLessonsDto
 * @Description:
 * @date 2021/3/25 20:40
 */
public class GiveLessonsDto {

    /**
     * 教师号
     */
    @NotNull(message = "教师号不能为空！")
    private String teacherId;

    /**
     * 课程号
     */
    @NotNull(message = "课程号不能为空！")
    private String courseId;

    /**
     * 学号集合
     */
    private String numbers;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    @Override
    public String toString() {
        return "GiveLessonsDto{" +
                "teacherId='" + teacherId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", numbers=" + numbers +
                '}';
    }
}
