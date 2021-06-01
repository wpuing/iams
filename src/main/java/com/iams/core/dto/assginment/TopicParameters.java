package com.iams.core.dto.assginment;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: TopicParameters
 * @Description:  题目详情参数
 * @date 2021/5/25 17:28
 */
public class TopicParameters implements Serializable {

    /**
     * 索引
     */
    private Integer index;

    /**
     * 类型
     */
    private Integer typeId;

    /**
     * 题目id
     */
    private Integer topicId;

    /**
     * 题目分数
     */
    private Float score;

    /**
     * 最高分
     */
    private Float topScore;

    /**
     * 最低分
     */
    private Float lowestScore;

    /**
     * 平均分
     */
    private Float average;

    /**
     * 人数
     */
    private Integer population;

    public Integer getIndex() {
        return index;
    }

    public TopicParameters setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public TopicParameters setTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public TopicParameters setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }

    public Float getScore() {
        return score;
    }

    public TopicParameters setScore(Float score) {
        this.score = score;
        return this;
    }

    public Float getTopScore() {
        return topScore;
    }

    public TopicParameters setTopScore(Float topScore) {
        this.topScore = topScore;
        return this;
    }

    public Float getLowestScore() {
        return lowestScore;
    }

    public TopicParameters setLowestScore(Float lowestScore) {
        this.lowestScore = lowestScore;
        return this;
    }

    public Float getAverage() {
        return average;
    }

    public TopicParameters setAverage(Float average) {
        this.average = average;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public TopicParameters setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    @Override
    public String toString() {
        return "TopicParameters{" +
                "index=" + index +
                ", typeId=" + typeId +
                ", topicId=" + topicId +
                ", score=" + score +
                ", topScore=" + topScore +
                ", lowestScore=" + lowestScore +
                ", average=" + average +
                ", population=" + population +
                '}';
    }
}
