package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 *   填空题答案
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Completion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题目号
     */
    private Integer topicId;

    /**
     * 答题内容
     */
    private String result;

    /**
     * 系统评分
     */
    private Float sysScore;

    /**
     * 教师评分
     */
    private Float teacherScore;

    public Integer getId() {
        return id;
    }

    public Completion setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getTopicId() {
        return topicId;
    }

    public Completion setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }
    public String getResult() {
        return result;
    }

    public Completion setResult(String result) {
        this.result = result;
        return this;
    }
    public Float getSysScore() {
        return sysScore;
    }

    public Completion setSysScore(Float sysScore) {
        this.sysScore = sysScore;
        return this;
    }
    public Float getTeacherScore() {
        return teacherScore;
    }

    public Completion setTeacherScore(Float teacherScore) {
        this.teacherScore = teacherScore;
        return this;
    }

    @Override
    public String toString() {
        return "Completion{" +
            "id=" + id +
            ", topicId=" + topicId +
            ", result=" + result +
            ", sysScore=" + sysScore +
            ", teacherScore=" + teacherScore +
        "}";
    }
}
