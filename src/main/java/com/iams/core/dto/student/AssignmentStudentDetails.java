package com.iams.core.dto.student;

import com.iams.core.dto.assginment.ChoiceDtoImpl;
import com.iams.core.dto.assginment.TopicDtoImpl;
import com.iams.core.pojo.Assignment;

import java.util.List;

/**
 * @author Wei yz
 * @ClassName: AssignmentDetailsImpl
 * @Description: 学生作业明细类，带答案id
 * @date 2021/5/7 13:38
 */
public class AssignmentStudentDetails {

    /**
     * 作业基本信息
     */
    private Assignment assignment;

    /**
     * 单选题
     */
    private List<ChoiceDtoImpl> singleChoiceList;

    /**
     * 多选题
     */
    private List<ChoiceDtoImpl> multipleChoiceList;


    /**
     * 判断题信息
     */
    private List<TopicDtoImpl> judgeTopicList;


    /**
     * 填空题信息
     */
    private List<TopicDtoImpl> completionTopicList;

    /**
     * 主观题信息
     */
    private List<TopicDtoImpl> subTopicList;

    public Assignment getAssignment() {
        return assignment;
    }

    public AssignmentStudentDetails setAssignment(Assignment assignment) {
        this.assignment = assignment;
        return this;
    }

    public List<ChoiceDtoImpl> getSingleChoiceList() {
        return singleChoiceList;
    }

    public AssignmentStudentDetails setSingleChoiceList(List<ChoiceDtoImpl> singleChoiceList) {
        this.singleChoiceList = singleChoiceList;
        return this;
    }

    public List<ChoiceDtoImpl> getMultipleChoiceList() {
        return multipleChoiceList;
    }

    public AssignmentStudentDetails setMultipleChoiceList(List<ChoiceDtoImpl> multipleChoiceList) {
        this.multipleChoiceList = multipleChoiceList;
        return this;
    }

    public List<TopicDtoImpl> getJudgeTopicList() {
        return judgeTopicList;
    }

    public AssignmentStudentDetails setJudgeTopicList(List<TopicDtoImpl> judgeTopicList) {
        this.judgeTopicList = judgeTopicList;
        return this;
    }

    public List<TopicDtoImpl> getCompletionTopicList() {
        return completionTopicList;
    }

    public AssignmentStudentDetails setCompletionTopicList(List<TopicDtoImpl> completionTopicList) {
        this.completionTopicList = completionTopicList;
        return this;
    }

    public List<TopicDtoImpl> getSubTopicList() {
        return subTopicList;
    }

    public AssignmentStudentDetails setSubTopicList(List<TopicDtoImpl> subTopicList) {
        this.subTopicList = subTopicList;
        return this;
    }

    @Override
    public String toString() {
        return "AssignmentStudentDetails{" +
                "assignment=" + assignment +
                ", singleChoiceList=" + singleChoiceList +
                ", multipleChoiceList=" + multipleChoiceList +
                ", judgeTopicList=" + judgeTopicList +
                ", completionTopicList=" + completionTopicList +
                ", subTopicList=" + subTopicList +
                '}';
    }
}
