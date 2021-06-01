package com.iams.core.dto.scores;

/**
 * @author Wei yz
 * @ClassName: StudentScoresDetails
 * @Description: 学生成绩明细信息
 * @date 2021/5/22 11:26
 */
public class StudentScoresDetails extends StudentScoresDto {

    /**
     * 题目索引
     */
    private Integer index;

    /**
     * 学生答案关系id
     */
    private Integer studentResultId;

    /**
     * 答案id
     */
    private Integer resultId;

    public Integer getIndex() {
        return index;
    }

    public StudentScoresDetails setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public Integer getStudentResultId() {
        return studentResultId;
    }

    public StudentScoresDetails setStudentResultId(Integer studentResultId) {
        this.studentResultId = studentResultId;
        return this;
    }

    public Integer getResultId() {
        return resultId;
    }

    public StudentScoresDetails setResultId(Integer resultId) {
        this.resultId = resultId;
        return this;
    }

    @Override
    public String toString() {
        return "StudentScoresDetails{" +
                "index=" + index +
                ", studentResultId=" + studentResultId +
                ", resultId=" + resultId +
                ", topicId=" + topicId +
                ", typeId=" + typeId +
                ", sysScores=" + sysScores +
                ", teacherScores=" + teacherScores +
                ", result='" + result + '\'' +
                '}';
    }
}
