package com.iams.core.dto.assginment;

import com.iams.core.dto.mcq.MultipleChoice;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: ChoiceDto
 * @Description: 选择题综合
 * @date 2021/4/5 12:41
 */
public class ChoiceDto implements Serializable {

    /**
     * 关系id
     */
    protected Integer relationId;
    /**
     * 作业id
     */
    protected Integer assignmentId;

    /**
     * 类型id
     */
    protected Integer typeId;

    /**
     * 题目id
     */
    protected Integer topicId;

    /**
     * 题目名称
     */
    protected MultipleChoice multipleChoice;

    /**
     * 分值
     */
    protected Float score;

    /**
     * 教师答案
     */
    protected String result;

    /**
     * 答题人数
     */
    protected Integer turnout;

    /**
     * 备注
     */
    protected String remark;

    public Integer getRelationId() {
        return relationId;
    }

    public ChoiceDto setRelationId(Integer relationId) {
        this.relationId = relationId;
        return this;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public ChoiceDto setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public ChoiceDto setTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public ChoiceDto setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }

    public MultipleChoice getMultipleChoice() {
        return multipleChoice;
    }

    public ChoiceDto setMultipleChoice(MultipleChoice multipleChoice) {
        this.multipleChoice = multipleChoice;
        return this;
    }

    public Float getScore() {
        return score;
    }

    public ChoiceDto setScore(Float score) {
        this.score = score;
        return this;
    }

    public String getResult() {
        return result;
    }

    public ChoiceDto setResult(String result) {
        this.result = result;
        return this;
    }

    public Integer getTurnout() {
        return turnout;
    }

    public ChoiceDto setTurnout(Integer turnout) {
        this.turnout = turnout;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public ChoiceDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public String toString() {
        return "ChoiceDto{" +
                "relationId=" + relationId +
                ", assignmentId=" + assignmentId +
                ", typeId=" + typeId +
                ", topicId=" + topicId +
                ", multipleChoice='" + multipleChoice + '\'' +
                ", score=" + score +
                ", result='" + result + '\'' +
                ", turnout=" + turnout +
                ", remark='" + remark + '\'' +
                '}';
    }
}
