package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *    客观题题目
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class ObjectiveTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作业号
     */
    @NotNull(message = "客观题作业号不能为空")
    private Integer assignmentId;

    /**
     * 题目名称
     */
    @NotNull(message = "客观题题目不能为空")
    private String title;

    /**
     * 分值
     */
    @NotNull(message = "客观题分数不能为空")
    private Float score;

    /**
     * 教师答案
     */
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

    public ObjectiveTopic setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getAssignmentId() {
        return assignmentId;
    }

    public ObjectiveTopic setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }
    public String getTitle() {
        return title;
    }

    public ObjectiveTopic setTitle(String title) {
        this.title = title;
        return this;
    }
    public Float getScore() {
        return score;
    }

    public ObjectiveTopic setScore(Float score) {
        this.score = score;
        return this;
    }
    public String getResult() {
        return result;
    }

    public ObjectiveTopic setResult(String result) {
        this.result = result;
        return this;
    }
    public Integer getTurnout() {
        return turnout;
    }

    public ObjectiveTopic setTurnout(Integer turnout) {
        this.turnout = turnout;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public ObjectiveTopic setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public ObjectiveTopic setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        return "ObjectiveTopic{" +
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
