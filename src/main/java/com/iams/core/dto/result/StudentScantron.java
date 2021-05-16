package com.iams.core.dto.result;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: StudentScantron
 * @Description: 学生答题卡
 * @date 2021/5/1 23:05
 */
public class StudentScantron implements Serializable {

    /**
     * 作业号
     */
    private Integer assignmentId;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 作答时间
     */
    private Long time;

    /**
     * 单答案
     */
    private String topicAnswerList;

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getTopicAnswerList() {
        return topicAnswerList;
    }

    public void setTopicAnswerList(String topicAnswerList) {
        this.topicAnswerList = topicAnswerList;
    }

    @Override
    public String toString() {
        return "StudentScantron1{" +
                "assignmentId=" + assignmentId +
                ", studentNumber='" + studentNumber + '\'' +
                ", time='" + time  +
                ", topicAnswerList=" + topicAnswerList +
                '}';
    }
}
