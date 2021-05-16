package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *   主观题题目
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class SubjectiveTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作业号
     */
    @NotNull(message = "主观题作业号不能为空")
    private Integer assignmentId;

    /**
     * 题目名称
     */
    @NotNull(message = "主观题题目不能为空")
    private String title;

    /**
     * 分值
     */
    @NotNull(message = "主观题分数不能为空")
    private Float score;

    /**
     * 教师答案
     */
    @NotNull(message = "主观题答案不能为空")
    private String result;

    /**
     * 答题人数
     */
    private Integer turnout;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除
     */
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public SubjectiveTopic setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getAssignmentId() {
        return assignmentId;
    }

    public SubjectiveTopic setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }
    public String getTitle() {
        return title;
    }

    public SubjectiveTopic setTitle(String title) {
        this.title = title;
        return this;
    }
    public Float getScore() {
        return score;
    }

    public SubjectiveTopic setScore(Float score) {
        this.score = score;
        return this;
    }
    public String getResult() {
        return result;
    }

    public SubjectiveTopic setResult(String result) {
        this.result = result;
        return this;
    }
    public Integer getTurnout() {
        return turnout;
    }

    public SubjectiveTopic setTurnout(Integer turnout) {
        this.turnout = turnout;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public SubjectiveTopic setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public SubjectiveTopic setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        return "SubjectiveTopic{" +
            "id=" + id +
            ", assignmentId=" + assignmentId +
            ", title=" + title +
            ", score=" + score +
            ", result=" + result +
            ", turnout=" + turnout +
            ", remark=" + remark +
            ", deleted=" + deleted +
        "}";
    }
}
