package com.iams.core.dto.student;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: StudentResultDto
 * @Description:
 * @date 2021/5/6 22:05
 */
public class StudentResultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer studentResultId;

    /**
     * 题目号
     */
    private Integer osTopicId;

    /**
     * 答案号
     */
    private Integer topicId;

    /**
     * 题型号
     */
    private Integer typeId;

    /**
     * 评分
     */
    private Float score;

    /**
     * 答案
     */
    private String result;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getStudentResultId() {
        return studentResultId;
    }

    public StudentResultDto setStudentResultId(Integer studentResultId) {
        this.studentResultId = studentResultId;
        return this;
    }

    public Integer getOsTopicId() {
        return osTopicId;
    }

    public StudentResultDto setOsTopicId(Integer osTopicId) {
        this.osTopicId = osTopicId;
        return this;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public StudentResultDto setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public StudentResultDto setTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    public Float getScore() {
        return score;
    }

    public StudentResultDto setScore(Float score) {
        this.score = score;
        return this;
    }

    public String getResult() {
        return result;
    }

    public StudentResultDto setResult(String result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return "StudentResultDto{" +
                "studentResultId=" + studentResultId +
                ", osTopicId=" + osTopicId +
                ", topicId=" + topicId +
                ", typeId=" + typeId +
                ", score=" + score +
                ", result='" + result + '\'' +
                '}';
    }
}
