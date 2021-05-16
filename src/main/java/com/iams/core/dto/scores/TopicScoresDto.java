package com.iams.core.dto.scores;

import com.iams.core.dto.assginment.TopicDto;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: TopicScoresDto
 * @Description:
 * @date 2021/5/8 15:52
 */
public class TopicScoresDto implements Serializable {

    /**
     * 题目信息
     */
    private TopicDto topicDto;

    /**
     * 答案
     */
    private StudentScoresDto studentScoresDto;

    public TopicDto getTopicDto() {
        return topicDto;
    }

    public TopicScoresDto setTopicDto(TopicDto topicDto) {
        this.topicDto = topicDto;
        return this;
    }

    public StudentScoresDto getStudentScoresDto() {
        return studentScoresDto;
    }

    public TopicScoresDto setStudentScoresDto(StudentScoresDto studentScoresDto) {
        this.studentScoresDto = studentScoresDto;
        return this;
    }

    @Override
    public String toString() {
        return "TopicScoresDto{" +
                "topicDto=" + topicDto +
                ", studentScoresInfo=" + studentScoresDto +
                '}';
    }
}
