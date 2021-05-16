package com.iams.core.dto.student;

import com.iams.core.dto.assginment.ChoiceDto;
import com.iams.core.dto.assginment.TopicDto;
import com.iams.core.pojo.Assignment;

import java.util.List;

/**
 * @author Wei yz
 * @ClassName: AssignmentDetails
 * @Description: 作业详细信息
 * @date 2021/4/30 18:43
 */
public class AssignmentDetails {
    /**
     * 作业基本信息
     */
    private Assignment assignment;

    /**
     * 单选题
     */
    private List<ChoiceDto> singleChoiceList;

    /**
     * 多选题
     */
    private List<ChoiceDto> multipleChoiceList;


    /**
     * 判断题信息
     */
    private List<TopicDto> judgeTopicList;


    /**
     * 填空题信息
     */
    private List<TopicDto> completionTopicList;

    /**
     * 主观题信息
     */
    private List<TopicDto> subTopicList;

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public List<ChoiceDto> getSingleChoiceList() {
        return singleChoiceList;
    }

    public void setSingleChoiceList(List<ChoiceDto> singleChoiceList) {
        this.singleChoiceList = singleChoiceList;
    }

    public List<ChoiceDto> getMultipleChoiceList() {
        return multipleChoiceList;
    }

    public void setMultipleChoiceList(List<ChoiceDto> multipleChoiceList) {
        this.multipleChoiceList = multipleChoiceList;
    }

    public List<TopicDto> getJudgeTopicList() {
        return judgeTopicList;
    }

    public void setJudgeTopicList(List<TopicDto> judgeTopicList) {
        this.judgeTopicList = judgeTopicList;
    }

    public List<TopicDto> getCompletionTopicList() {
        return completionTopicList;
    }

    public void setCompletionTopicList(List<TopicDto> completionTopicList) {
        this.completionTopicList = completionTopicList;
    }

    public List<TopicDto> getSubTopicList() {
        return subTopicList;
    }

    public void setSubTopicList(List<TopicDto> subTopicList) {
        this.subTopicList = subTopicList;
    }

    @Override
    public String toString() {
        return "AssignmentDetails{" +
                "assignment=" + assignment +
                ", singleChoiceList=" + singleChoiceList +
                ", multipleChoiceList=" + multipleChoiceList +
                ", judgeTopicList=" + judgeTopicList +
                ", completionTopicList=" + completionTopicList +
                ", subTopicList=" + subTopicList +
                '}';
    }
}
