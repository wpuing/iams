package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 *   学生答案关系
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class StudentResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
     * 作业号
     */
    private Integer assignmentId;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 评分
     */
    private Float score;

    public Integer getId() {
        return id;
    }

    public StudentResult setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getOsTopicId() {
        return osTopicId;
    }

    public StudentResult setOsTopicId(Integer osTopicId) {
        this.osTopicId = osTopicId;
        return this;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public StudentResult setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }
    public Integer getTypeId() {
        return typeId;
    }

    public StudentResult setTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }
    public Integer getAssignmentId() {
        return assignmentId;
    }

    public StudentResult setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }
    public String getStudentId() {
        return studentId;
    }

    public StudentResult setStudentId(String studentId) {
        this.studentId = studentId;
        return this;
    }
    public Float getScore() {
        return score;
    }

    public StudentResult setScore(Float score) {
        this.score = score;
        return this;
    }

    public StudentResult() {

    }

    public StudentResult(Integer id, Integer osTopicId, Integer topicId, Integer typeId, Integer assignmentId, String studentId, Float score) {
        this.id = id;
        this.osTopicId = osTopicId;
        this.topicId = topicId;
        this.typeId = typeId;
        this.assignmentId = assignmentId;
        this.studentId = studentId;
        this.score = score;
    }

    @Override
    public String toString() {
        return "StudentResult{" +
            "id=" + id +
            ", osTopicId=" + osTopicId +
            ", topicId=" + topicId +
            ", typeId=" + typeId +
            ", assignmentId=" + assignmentId +
            ", studentId=" + studentId +
            ", score=" + score +
        "}";
    }
}
