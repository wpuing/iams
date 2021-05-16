package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 *   主观题答案
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Subjective implements Serializable {

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
     * 文件
     */
    private String file;

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

    public Subjective setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getTopicId() {
        return topicId;
    }

    public Subjective setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }
    public String getResult() {
        return result;
    }

    public Subjective setResult(String result) {
        this.result = result;
        return this;
    }
    public String getFile() {
        return file;
    }

    public Subjective setFile(String file) {
        this.file = file;
        return this;
    }
    public Float getSysScore() {
        return sysScore;
    }

    public Subjective setSysScore(Float sysScore) {
        this.sysScore = sysScore;
        return this;
    }
    public Float getTeacherScore() {
        return teacherScore;
    }

    public Subjective setTeacherScore(Float teacherScore) {
        this.teacherScore = teacherScore;
        return this;
    }

    @Override
    public String toString() {
        return "Subjective{" +
            "id=" + id +
            ", topicId=" + topicId +
            ", result=" + result +
            ", file=" + file +
            ", sysScore=" + sysScore +
            ", teacherScore=" + teacherScore +
        "}";
    }
}
