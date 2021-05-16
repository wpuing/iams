package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei yz
 * @ClassName: StudentTask
 * @Description: 学生作业关系
 * @date 2021/4/17 21:50
 */
public class StudentTask implements Serializable {

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
    private String studentId;

    /**
     * 作业编号
     */
    @NotNull(message = "作业编号不能为空")
    private Integer assignmentId;

    /**
     * 是否允许答题 0允许 1不允许
     */
    private Integer isAnswer;


    /**
     * 开始答题时间
     */
    private Date startQuizTime;

    /**
     * 总答题时间
     */
    private String totalQuizTime;

    public Integer getId() {
        return id;
    }

    public StudentTask setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getStudentId() {
        return studentId;
    }

    public StudentTask setStudentId(String studentId) {
        this.studentId = studentId;
        return this;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public StudentTask setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }

    public Integer getIsAnswer() {
        return isAnswer;
    }

    public StudentTask setIsAnswer(Integer isAnswer) {
        this.isAnswer = isAnswer;
        return this;
    }

    public Date getStartQuizTime() {
        return startQuizTime;
    }

    public StudentTask setStartQuizTime(Date startQuizTime) {
        this.startQuizTime = startQuizTime;
        return this;
    }

    public String getTotalQuizTime() {
        return totalQuizTime;
    }

    public StudentTask setTotalQuizTime(String totalQuizTime) {
        this.totalQuizTime = totalQuizTime;
        return this;
    }

    public StudentTask() {
    }

    public StudentTask(Integer id, String studentId, Integer assignmentId, Integer isAnswer, Date startQuizTime, String totalQuizTime) {
        this.id = id;
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.isAnswer = isAnswer;
        this.startQuizTime = startQuizTime;
        this.totalQuizTime = totalQuizTime;
    }

    @Override
    public String toString() {
        return "StudentTask{" +
                "id=" + id +
                ", studentId='" + studentId + '\'' +
                ", assignmentId=" + assignmentId +
                ", isAnswer=" + isAnswer +
                ", startQuizTime=" + startQuizTime +
                ", totalQuizTime='" + totalQuizTime + '\'' +
                '}';
    }
}
