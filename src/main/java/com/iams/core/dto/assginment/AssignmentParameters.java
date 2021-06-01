package com.iams.core.dto.assginment;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wei yz
 * @ClassName: AssignmentParameters
 * @Description:  作业详情参数
 * @date 2021/5/25 17:24
 */
public class AssignmentParameters implements Serializable {

    /**
     * 作业号
     */
    private Integer assignmentId;

    /**
     * 人数
     */
    private Integer population;

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
     * 题目详细参数
     */
    private List<TopicParameters> parametersList;

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public AssignmentParameters setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }

    public Integer getPopulation() {
        return population;
    }

    public AssignmentParameters setPopulation(Integer population) {
        this.population = population;
        return this;
    }

    public Float getTopScore() {
        return topScore;
    }

    public AssignmentParameters setTopScore(Float topScore) {
        this.topScore = topScore;
        return this;
    }

    public Float getLowestScore() {
        return lowestScore;
    }

    public AssignmentParameters setLowestScore(Float lowestScore) {
        this.lowestScore = lowestScore;
        return this;
    }

    public Float getAverage() {
        return average;
    }

    public AssignmentParameters setAverage(Float average) {
        this.average = average;
        return this;
    }

    public List<TopicParameters> getParametersList() {
        return parametersList;
    }

    public AssignmentParameters setParametersList(List<TopicParameters> parametersList) {
        this.parametersList = parametersList;
        return this;
    }

    @Override
    public String toString() {
        return "AssignmentParameters{" +
                "assignmentId=" + assignmentId +
                ", population=" + population +
                ", topScore=" + topScore +
                ", lowestScore=" + lowestScore +
                ", average=" + average +
                ", parametersList=" + parametersList +
                '}';
    }
}
