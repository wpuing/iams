package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 *   多选题答案
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Multiterm implements Serializable {

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
     * 评分
     */
    private Float score;

    public Integer getId() {
        return id;
    }

    public Multiterm setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getTopicId() {
        return topicId;
    }

    public Multiterm setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }
    public String getResult() {
        return result;
    }

    public Multiterm setResult(String result) {
        this.result = result;
        return this;
    }
    public Float getScore() {
        return score;
    }

    public Multiterm setScore(Float score) {
        this.score = score;
        return this;
    }

    @Override
    public String toString() {
        return "Multiterm{" +
            "id=" + id +
            ", topicId=" + topicId +
            ", result=" + result +
            ", score=" + score +
        "}";
    }
}
