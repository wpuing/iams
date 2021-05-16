package com.iams.core.dto.student;

import com.iams.core.pojo.Assignment;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Wei yz
 * @ClassName: StudentTaskDto
 * @Description:
 * @date 2021/4/29 19:36
 */
public class StudentTaskDto implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 作业编号
     */
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

    /**
     * 作业信息
     */
    private Assignment assignment;

    public Integer getId() {
        return id;
    }

    public StudentTaskDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public StudentTaskDto setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }

    public Integer getIsAnswer() {
        return isAnswer;
    }

    public StudentTaskDto setIsAnswer(Integer isAnswer) {
        this.isAnswer = isAnswer;
        return this;
    }

    public Date getStartQuizTime() {
        return startQuizTime;
    }

    public StudentTaskDto setStartQuizTime(Date startQuizTime) {
        this.startQuizTime = startQuizTime;
        return this;
    }

    public String getTotalQuizTime() {
        return totalQuizTime;
    }

    public StudentTaskDto setTotalQuizTime(String totalQuizTime) {
        this.totalQuizTime = totalQuizTime;
        return this;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public StudentTaskDto setAssignment(Assignment assignment) {
        this.assignment = assignment;
        return this;
    }

    @Override
    public String toString() {
        return "StudentTaskDto{" +
                "id=" + id +
                ", assignmentId=" + assignmentId +
                ", isAnswer=" + isAnswer +
                ", startQuizTime=" + startQuizTime +
                ", totalQuizTime='" + totalQuizTime + '\'' +
                ", assignment=" + assignment +
                '}';
    }
}
