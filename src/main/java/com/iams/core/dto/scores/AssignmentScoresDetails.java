package com.iams.core.dto.scores;

import com.iams.core.pojo.Assignment;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wei yz
 * @ClassName: AssignmentScoresDetails
 * @Description:
 * @date 2021/5/8 15:40
 */
public class AssignmentScoresDetails implements Serializable {

    /**
     * 作业基本信息
     */
    private Assignment assignment;

    /**
     * 单选题
     */
    private List<ChoiceScoresDto> singleChoiceList;

    /**
     * 多选题
     */
    private List<ChoiceScoresDto> multipleChoiceList;


    /**
     * 判断题信息
     */
    private List<TopicScoresDto> judgeTopicList;


    /**
     * 填空题信息
     */
    private List<TopicScoresDto> completionTopicList;

    /**
     * 主观题信息
     */
    private List<TopicScoresDto> subTopicList;

    public Assignment getAssignment() {
        return assignment;
    }

    public AssignmentScoresDetails setAssignment(Assignment assignment) {
        this.assignment = assignment;
        return this;
    }

    public List<ChoiceScoresDto> getSingleChoiceList() {
        return singleChoiceList;
    }

    public AssignmentScoresDetails setSingleChoiceList(List<ChoiceScoresDto> singleChoiceList) {
        this.singleChoiceList = singleChoiceList;
        return this;
    }

    public List<ChoiceScoresDto> getMultipleChoiceList() {
        return multipleChoiceList;
    }

    public AssignmentScoresDetails setMultipleChoiceList(List<ChoiceScoresDto> multipleChoiceList) {
        this.multipleChoiceList = multipleChoiceList;
        return this;
    }

    public List<TopicScoresDto> getJudgeTopicList() {
        return judgeTopicList;
    }

    public AssignmentScoresDetails setJudgeTopicList(List<TopicScoresDto> judgeTopicList) {
        this.judgeTopicList = judgeTopicList;
        return this;
    }

    public List<TopicScoresDto> getCompletionTopicList() {
        return completionTopicList;
    }

    public AssignmentScoresDetails setCompletionTopicList(List<TopicScoresDto> completionTopicList) {
        this.completionTopicList = completionTopicList;
        return this;
    }

    public List<TopicScoresDto> getSubTopicList() {
        return subTopicList;
    }

    public AssignmentScoresDetails setSubTopicList(List<TopicScoresDto> subTopicList) {
        this.subTopicList = subTopicList;
        return this;
    }

    @Override
    public String toString() {
        return "AssignmentScoresDetails{" +
                "assignment=" + assignment +
                ", singleChoiceList=" + singleChoiceList +
                ", multipleChoiceList=" + multipleChoiceList +
                ", judgeTopicList=" + judgeTopicList +
                ", completionTopicList=" + completionTopicList +
                ", subTopicList=" + subTopicList +
                '}';
    }
}
