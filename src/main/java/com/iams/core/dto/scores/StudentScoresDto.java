package com.iams.core.dto.scores;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: StudentScoresInfo
 * @Description:  学生作业成绩答案类
 * @date 2021/5/8 13:57
 */
public class StudentScoresDto implements Serializable {

    /**
     * 作业id
     */
    protected Integer topicId;

    /**
     * 类型id
     */
    protected Integer typeId;

    /**
     * 系统预评分
     */
    protected Float sysScores;

    /**
     * 教师打分
     */
    protected Float teacherScores;

    /**
     * 答案
     */
    protected String result;

    public Integer getTopicId() {
        return topicId;
    }

    public StudentScoresDto setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public StudentScoresDto setTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    public Float getSysScores() {
        return sysScores;
    }

    public StudentScoresDto setSysScores(Float sysScores) {
        this.sysScores = sysScores;
        return this;
    }

    public Float getTeacherScores() {
        return teacherScores;
    }

    public StudentScoresDto setTeacherScores(Float teacherScores) {
        this.teacherScores = teacherScores;
        return this;
    }

    public String getResult() {
        return result;
    }

    public StudentScoresDto setResult(String result) {
        this.result = result;
        return this;
    }

    @Override
    public String toString() {
        return "StudentScoresInfo{" +
                "topicId=" + topicId +
                ", typeId=" + typeId +
                ", sysScores=" + sysScores +
                ", teacherScores=" + teacherScores +
                ", result='" + result + '\'' +
                '}';
    }
}
