package com.iams.core.dto;

/**
 * @author Wei yz
 * @ClassName: ResultData
 * @Description:
 * @date 2021/4/18 11:23
 */
public class ResultData {
    private Integer assignmentId;
    private String numbers;

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public ResultData setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }

    public String getNumbers() {
        return numbers;
    }

    public ResultData setNumbers(String numbers) {
        this.numbers = numbers;
        return this;
    }

    @Override
    public String toString() {
        return "ResultData{" +
                "assignmentId=" + assignmentId +
                ", numbers=" + numbers +
                '}';
    }
}
