package com.iams.core.dto.scores;

import com.iams.core.dto.assginment.ChoiceDto;

/**
 * @author Wei yz
 * @ClassName: StudentAssignmentScoresDto
 * @Description: 学生作业成绩类
 * @date 2021/5/8 14:04
 */
public class ChoiceScoresDto {

    /**
     * 题目信息
     */
    private ChoiceDto choiceDto;

    /**
     * 答案
     */
    private StudentScoresDto studentScoresDto;

    public ChoiceDto getChoiceDto() {
        return choiceDto;
    }

    public ChoiceScoresDto setChoiceDto(ChoiceDto choiceDto) {
        this.choiceDto = choiceDto;
        return this;
    }

    public StudentScoresDto getStudentScoresDto() {
        return studentScoresDto;
    }

    public ChoiceScoresDto setStudentScoresDto(StudentScoresDto studentScoresDto) {
        this.studentScoresDto = studentScoresDto;
        return this;
    }

    public ChoiceScoresDto() {
        if (studentScoresDto == null) studentScoresDto = new StudentScoresDto();
    }

    @Override
    public String toString() {
        return "ChoiceScoresDto{" +
                "choiceDto=" + choiceDto +
                ", studentScoresDto=" + studentScoresDto +
                '}';
    }
}
