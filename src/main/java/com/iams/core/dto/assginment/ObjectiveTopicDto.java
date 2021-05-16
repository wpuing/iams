package com.iams.core.dto.assginment;

import com.iams.core.dto.mcq.MultipleChoice;

import java.io.Serializable;

/**
 * <p>
 *    选择题题目
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class ObjectiveTopicDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Integer id;

    /**
     * 作业号
     */
    private Integer assignmentId;

    /**
     * 题目名称
     */
    private MultipleChoice multipleChoice;

    /**
     * 分值
     */
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

    public ObjectiveTopicDto setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getAssignmentId() {
        return assignmentId;
    }

    public ObjectiveTopicDto setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }

    public MultipleChoice getMultipleChoice() {
        return multipleChoice;
    }

    public ObjectiveTopicDto setMultipleChoice(MultipleChoice multipleChoice) {
        this.multipleChoice = multipleChoice;
        return this;
    }

    public Float getScore() {
        return score;
    }

    public ObjectiveTopicDto setScore(Float score) {
        this.score = score;
        return this;
    }
    public String getResult() {
        return result;
    }

    public ObjectiveTopicDto setResult(String result) {
        this.result = result;
        return this;
    }
    public Integer getTurnout() {
        return turnout;
    }

    public ObjectiveTopicDto setTurnout(Integer turnout) {
        this.turnout = turnout;
        return this;
    }
    public String getRemark() {
        return remark;
    }

    public ObjectiveTopicDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public ObjectiveTopicDto setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        return "ObjectiveTopic{" +
            "id=" + id +
            ", assignmentId=" + assignmentId +
            ", multipleChoice=" + multipleChoice +
            ", score=" + score +
            ", result=" + result +
            ", turnout=" + turnout +
            ", remark=" + remark +
            ", deleted=" + deleted +
        "}";
    }
}
