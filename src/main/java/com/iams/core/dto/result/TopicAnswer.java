package com.iams.core.dto.result;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: TopicAnswer
 * @Description: 题目内容
 * @date 2021/5/1 23:08
 */
public class TopicAnswer implements Serializable {

    /**
     * 类型
     */
    private Integer type;

    /**
     * 题目id
     */
    private Integer topicId;

    /**
     * 答案号
     */
    private Integer childId;

    /**
     * 学生答案关系id
     */
    private Integer studentResultId;

    /**
     * 答题内容
     */
    private String result;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public TopicAnswer setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }

    public Integer getChildId() {
        return childId;
    }

    public TopicAnswer setChildId(Integer childId) {
        this.childId = childId;
        return this;
    }

    public Integer getStudentResultId() {
        return studentResultId;
    }

    public TopicAnswer setStudentResultId(Integer studentResultId) {
        this.studentResultId = studentResultId;
        return this;
    }

    public String getResult() {
        return result;
    }

    public TopicAnswer setResult(String result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return "TopicAnswer{" +
                "type=" + type +
                ", topicId=" + topicId +
                ", childId=" + childId +
                ", studentResultId=" + studentResultId +
                ", result='" + result + '\'' +
                '}';
    }
}
