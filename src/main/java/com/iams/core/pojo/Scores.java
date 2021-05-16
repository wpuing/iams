package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 *   成绩
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Scores implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 学号
     */
    private String studentId;

    /**
     * 作业号
     */
    private Integer assignmentId;

    /**
     * 成绩
     */
    private Float score;

    /**
     * 评语
     */
    private String comment;

    public Integer getId() {
        return id;
    }

    public Scores setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getStudentId() {
        return studentId;
    }

    public Scores setStudentId(String studentId) {
        this.studentId = studentId;
        return this;
    }
    public Integer getAssignmentId() {
        return assignmentId;
    }

    public Scores setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }
    public Float getScore() {
        return score;
    }

    public Scores setScore(Float score) {
        this.score = score;
        return this;
    }
    public String getComment() {
        return comment;
    }

    public Scores setComment(String comment) {
        this.comment = comment;
        return this;
    }

    @Override
    public String toString() {
        return "Scores{" +
            "id=" + id +
            ", studentId=" + studentId +
            ", assignmentId=" + assignmentId +
            ", score=" + score +
            ", comment=" + comment +
        "}";
    }
}
