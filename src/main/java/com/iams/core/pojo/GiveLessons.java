package com.iams.core.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

/**
 * <p>
 *   课程学生教师
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class GiveLessons implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学号
     */
    @Excel(name = "学号", width = 14)
    private String studentId;

    /**
     * 教师号
     */
    private String teacherId;

    /**
     * 课程号
     */
    private String courseId;

    public Integer getId() {
        return id;
    }

    public GiveLessons setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getStudentId() {
        return studentId;
    }

    public GiveLessons setStudentId(String studentId) {
        this.studentId = studentId;
        return this;
    }
    public String getTeacherId() {
        return teacherId;
    }

    public GiveLessons setTeacherId(String teacherId) {
        this.teacherId = teacherId;
        return this;
    }
    public String getCourseId() {
        return courseId;
    }

    public GiveLessons setCourseId(String courseId) {
        this.courseId = courseId;
        return this;
    }

    @Override
    public String toString() {
        return "GiveLessons{" +
            "id=" + id +
            ", studentId=" + studentId +
            ", teacherId=" + teacherId +
            ", courseId=" + courseId +
        "}";
    }
}
