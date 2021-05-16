package com.iams.core.dto.assginment;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: ObjectiveTopicDto
 * @Description:  客观题视图类
 * @date 2021/4/4 19:34
 */
public class TopicDto implements Serializable {

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
    protected String title;

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

    public TopicDto setRelationId(Integer relationId) {
        this.relationId = relationId;
        return this;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public TopicDto setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public TopicDto setTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public TopicDto setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TopicDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public Float getScore() {
        return score;
    }

    public TopicDto setScore(Float score) {
        this.score = score;
        return this;
    }

    public String getResult() {
        return result;
    }

    public TopicDto setResult(String result) {
        this.result = result;
        return this;
    }

    public Integer getTurnout() {
        return turnout;
    }

    public TopicDto setTurnout(Integer turnout) {
        this.turnout = turnout;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public TopicDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public String toString() {
        return "TopicDto{" +
                "relationId=" + relationId +
                ", assignmentId=" + assignmentId +
                ", typeId=" + typeId +
                ", topicId=" + topicId +
                ", title='" + title + '\'' +
                ", score=" + score +
                ", result='" + result + '\'' +
                ", turnout=" + turnout +
                ", remark='" + remark + '\'' +
                '}';
    }
}
