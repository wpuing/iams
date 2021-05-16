package com.iams.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.iams.common.constant.IamsConstants;
import com.iams.common.util.IamsUtils;
import com.iams.common.util.Utils;
import com.iams.core.dto.assginment.ChoiceDto;
import com.iams.core.dto.assginment.TopicDto;
import com.iams.core.dto.result.StudentScantron;
import com.iams.core.dto.result.TopicAnswer;
import com.iams.core.dto.student.AssignmentDetails;
import com.iams.core.pojo.*;
import com.iams.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Wei yz
 * @ClassName: CorrectServiceImpl
 * @Description:
 * @date 2021/5/4 17:00
 */
@Service
public class CorrectServiceImpl implements CorrectService {

    @Autowired
    private SingleService singleService;//单选题
    @Autowired
    private MultitermService multitermService;//多选题
    @Autowired
    private JudgmentService judgmentService;//判断题
    @Autowired
    private CompletionService completionService;//填空题
    @Autowired
    private SubjectiveService subjectiveService;//主观题
    @Autowired
    private StudentResultService studentResultService;//学生答案


    @Override
    public Float checkResult(String studentResult, String teacherResult, Long time, Float score, Integer type) {

        if (!Utils.isEmpty(studentResult)) {//学生答案为空则成绩为0.0
            return 0.0f;
        }
        if (type == IamsConstants.TOPIC_TYPE[0] || type == IamsConstants.TOPIC_TYPE[2]) {//单选题，判断题
            if (!studentResult.equals(teacherResult)) {//学生答案与教师答案不一致则成绩为0.0
                return 0.0f;
            }
        }
        if (type == IamsConstants.TOPIC_TYPE[1]) {//多选题
            //把,号去除，然后判断答案是否符合规则，就返回分数，规则：多选题全对得满分，多选一个错误答案得零分，漏选得一半分。
            float s = checkMultipleChoice(IamsUtils.remover(studentResult), IamsUtils.remover(teacherResult), score);
            return (float) Math.rint(s);//四舍五入，强转为float
        }
        if (type == IamsConstants.TOPIC_TYPE[3]) {//填空题
            if (!studentResult.toLowerCase().equals(teacherResult.toLowerCase())) {//转小写判断，不一致则0.0
                return 0.0f;
            }
        }
        if (type == IamsConstants.TOPIC_TYPE[4]) {//主观题
            Float sim = SubjectiveResultService.sim(studentResult, teacherResult, (float) Math.rint(score * 0.7));
            Float getsScore = SubjectiveResultService.getsScore(studentResult, teacherResult, time, (float) Math.rint(score * 0.3));
            return (float) Math.rint(sim + getsScore);
        }
        return score;
    }

    @Override
    public Float correct(StudentScantron studentScantron, AssignmentDetails assignmentDetails) {
        //1.拿出答题卡的题目
        List<TopicAnswer> topicAnswerList = JSON.parseArray(studentScantron.getTopicAnswerList(), TopicAnswer.class);
        Float f1 = 0.0f;//单选题分数
        Float f2 = 0.0f;//多选题分数
        Float f3 = 0.0f;//判断题分数
        Float f4 = 0.0f;//填空题分数
        Float f5 = 0.0f;//主观题分数
        if (CollectionUtils.isEmpty(topicAnswerList)) {//答题卡为空
            return 0.0f;
        }
        for (TopicAnswer topicAnswer : topicAnswerList) {
            if (!CollectionUtils.isEmpty(assignmentDetails.getSingleChoiceList()) && topicAnswer.getType() == IamsConstants.TOPIC_TYPE[0]) {//单选题
                for (ChoiceDto choiceDto : assignmentDetails.getSingleChoiceList()) {//遍历作业的单选题
                    if (topicAnswer.getTopicId() == choiceDto.getTopicId()) {//相同题目
                        Float score = checkResult(topicAnswer.getResult(), choiceDto.getResult(), studentScantron.getTime(), choiceDto.getScore(), choiceDto.getTypeId());
                        f1 += score;//累计分数
                        //将答案添加或修改到数据库
                        Single single = new Single()
                                .setResult(topicAnswer.getResult())
                                .setScore(score)
                                .setTopicId(topicAnswer.getTopicId());
                        StudentResult studentResult = new StudentResult()
                                .setTypeId(topicAnswer.getType())
                                .setAssignmentId(studentScantron.getAssignmentId())
                                .setScore(score)
                                .setStudentId(studentScantron.getStudentNumber())
                                .setOsTopicId(topicAnswer.getTopicId())
                                .setTopicId(single.getId());
                        if(Utils.isEmpty(topicAnswer.getStudentResultId())){//当学生关系id不为空时，已经存在答案，为修改
                            single.setId(topicAnswer.getChildId());
                            singleService.update(single);//添加答案到答案表
                            studentResult.setId(topicAnswer.getStudentResultId()).setTopicId(topicAnswer.getChildId());
                            studentResultService.update(studentResult);//添加学生答案关系
                        }
                        if(!Utils.isEmpty(topicAnswer.getStudentResultId())){//当学生关系id为空时，为添加
                            singleService.insert(single);//添加答案到答案表
                            studentResult.setTopicId(single.getId());//添加答案的id
                            studentResultService.insert(studentResult);//添加学生答案关系
                        }
                        System.out.println("第 " + topicAnswer.getTopicId() + "题单选题分数：" + f1);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(assignmentDetails.getMultipleChoiceList()) && topicAnswer.getType() == IamsConstants.TOPIC_TYPE[1]) {//多选题
                for (ChoiceDto choiceDto : assignmentDetails.getMultipleChoiceList()) {//遍历作业的多选题
                    if (topicAnswer.getTopicId() == choiceDto.getTopicId()) {//相同题目
                        Float score = checkResult(topicAnswer.getResult(), choiceDto.getResult(), studentScantron.getTime(), choiceDto.getScore(), choiceDto.getTypeId());
                        f2 += score;
                        //将答案添加到数据库
                        Multiterm multiterm = new Multiterm()
                                .setResult(topicAnswer.getResult())
                                .setScore(score)
                                .setTopicId(topicAnswer.getTopicId());
                        StudentResult studentResult = new StudentResult()
                                .setTypeId(topicAnswer.getType())
                                .setAssignmentId(studentScantron.getAssignmentId())
                                .setScore(score)
                                .setStudentId(studentScantron.getStudentNumber())
                                .setOsTopicId(topicAnswer.getTopicId())
                                .setTopicId(multiterm.getId());
                        if(Utils.isEmpty(topicAnswer.getStudentResultId())) {//当学生关系id不为空时，已经存在答案，为修改
                            multiterm.setId(topicAnswer.getChildId());
                            multitermService.update(multiterm);//添加答案到答案表
                            studentResult.setId(topicAnswer.getStudentResultId()).setTopicId(topicAnswer.getChildId());
                            studentResultService.update(studentResult);//添加学生答案关系
                        }
                        if(!Utils.isEmpty(topicAnswer.getStudentResultId())) {//当学生关系id为空时，不存在答案，为添加
                            multitermService.insert(multiterm);//添加答案到答案表
                            studentResult.setTopicId(multiterm.getId());//添加答案的id
                            studentResultService.insert(studentResult);//添加学生答案关系
                        }
                        System.out.println("第 " + topicAnswer.getTopicId() + "题多选题分数：" + f2);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(assignmentDetails.getJudgeTopicList()) && topicAnswer.getType() == IamsConstants.TOPIC_TYPE[2]) {//判断题
                for (TopicDto topicDto : assignmentDetails.getJudgeTopicList()) {//遍历作业的判断题
                    if (topicAnswer.getTopicId() == topicDto.getTopicId()) {//相同题目
                        Float score = checkResult(topicAnswer.getResult(), topicDto.getResult(), studentScantron.getTime(), topicDto.getScore(), topicDto.getTypeId());
                        f3 += score;
                        Judgment judgment = new Judgment()
                                .setResult(topicAnswer.getResult())
                                .setScore(score)
                                .setTopicId(topicAnswer.getTopicId());
                        StudentResult studentResult = new StudentResult()
                                .setTypeId(topicAnswer.getType())
                                .setAssignmentId(studentScantron.getAssignmentId())
                                .setScore(score)
                                .setStudentId(studentScantron.getStudentNumber())
                                .setOsTopicId(topicAnswer.getTopicId())
                                .setTopicId(judgment.getId());
                        if(Utils.isEmpty(topicAnswer.getStudentResultId())) {//当学生关系id不为空时，已经存在答案，为修改
                            judgment.setId(topicAnswer.getChildId());
                            judgmentService.update(judgment);//添加答案到答案表
                            studentResult.setId(topicAnswer.getStudentResultId()).setTopicId(topicAnswer.getChildId());
                            studentResultService.update(studentResult);//添加学生答案关系
                        }
                        if(!Utils.isEmpty(topicAnswer.getStudentResultId())) {//当学生关系id为空时，不存在答案，为添加
                            judgmentService.insert(judgment);//添加答案到答案表
                            studentResult.setTopicId(judgment.getId());//添加答案的id
                            studentResultService.insert(studentResult);//添加学生答案关系
                        }
                        System.out.println("第 " + topicAnswer.getTopicId() + "题判断题分数：" + f3);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(assignmentDetails.getCompletionTopicList()) && topicAnswer.getType() == IamsConstants.TOPIC_TYPE[3]) {//填空题
                for (TopicDto topicDto : assignmentDetails.getCompletionTopicList()) {//遍历作业的填空题
                    if (topicAnswer.getTopicId() == topicDto.getTopicId()) {//相同题目
                        Float score = checkResult(topicAnswer.getResult(), topicDto.getResult(), studentScantron.getTime(), topicDto.getScore(), topicDto.getTypeId());
                        f4 += score;
                        Completion completion = new Completion()
                                .setResult(topicAnswer.getResult())
                                .setSysScore(score)//预估分
                                .setTopicId(topicAnswer.getTopicId());
                        StudentResult studentResult = new StudentResult()
                                .setTypeId(topicAnswer.getType())
                                .setAssignmentId(studentScantron.getAssignmentId())
                                .setScore(score)
                                .setStudentId(studentScantron.getStudentNumber())
                                .setOsTopicId(topicAnswer.getTopicId())
                                .setTopicId(completion.getId());
                        if(Utils.isEmpty(topicAnswer.getStudentResultId())) {//当学生关系id不为空时，已经存在答案，为修改
                            completion.setId(topicAnswer.getChildId());
                            completionService.update(completion);//添加答案到答案表
                            studentResult.setId(topicAnswer.getStudentResultId()).setTopicId(topicAnswer.getChildId());
                            studentResultService.update(studentResult);//添加学生答案关系
                        }
                        if(!Utils.isEmpty(topicAnswer.getStudentResultId())) {//当学生关系id为空时，不存在答案，为添加
                            completionService.insert(completion);//添加答案到答案表
                            studentResult.setTopicId(completion.getId());//添加答案的id
                            studentResultService.insert(studentResult);//添加学生答案关系
                        }
                        System.out.println("第 " + topicAnswer.getTopicId() + "题填空题分数：" + f4);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(assignmentDetails.getSubTopicList()) && topicAnswer.getType() == IamsConstants.TOPIC_TYPE[4]) {//主观题
                for (TopicDto topicDto : assignmentDetails.getSubTopicList()) {//遍历作业的主观题
                    if (topicAnswer.getTopicId() == topicDto.getTopicId()) {//相同题目

                        Float score = checkResult(topicAnswer.getResult(), topicDto.getResult(), studentScantron.getTime(), topicDto.getScore(), topicDto.getTypeId());
                        f5 += score;
                        Subjective subjective = new Subjective()
                                .setResult(topicAnswer.getResult())
                                .setSysScore(score)//预估分
                                .setTopicId(topicAnswer.getTopicId());
                        StudentResult studentResult = new StudentResult()
                                .setTypeId(topicAnswer.getType())
                                .setAssignmentId(studentScantron.getAssignmentId())
                                .setScore(score)
                                .setStudentId(studentScantron.getStudentNumber())
                                .setOsTopicId(topicAnswer.getTopicId())
                                .setTopicId(subjective.getId());
                        if(Utils.isEmpty(topicAnswer.getStudentResultId())) {//当学生关系id不为空时，已经存在答案，为修改
                            subjective.setId(topicAnswer.getChildId());
                            subjectiveService.update(subjective);//添加答案到答案表
                            studentResult.setId(topicAnswer.getStudentResultId()).setTopicId(topicAnswer.getChildId());
                            studentResultService.update(studentResult);//添加学生答案关系
                        }
                        if(!Utils.isEmpty(topicAnswer.getStudentResultId())) {//当学生关系id为空时，不存在答案，为添加
                            subjectiveService.insert(subjective);//添加答案到答案表
                            studentResult.setTopicId(subjective.getId());//添加答案的id
                            studentResultService.insert(studentResult);//添加学生答案关系
                        }
                        System.out.println("第 " + topicAnswer.getTopicId() + "题主观题分数：" + f5);
                    }
                }
            }
        }
        return (f1 + f2 + f3 + f4 + f5);
    }


    /**
     * 选择题得分，多选题全对得满分，多选一个错误答案得零分，漏选得一半分。
     *
     * @param answer         需要检查的内容
     * @param standardAnswer 答案
     * @param score          分数
     * @return
     */
    private float checkMultipleChoice(String answer, String standardAnswer, float score) {
        float newScore = 0f;
        if (standardAnswer.length() == 1)// 单选
        {
            if (standardAnswer.equals(answer)) {
                newScore = score;
            }
        } else// 多选题
        {
            if (answer.length() < standardAnswer.length()) {
                char[] answerArr = answer.toCharArray();
                // 判断answerArr中的字符是否全在standardAnswerArr中 如果是就得一半分
                int flag = 1;// 标志，一旦有一个字符不在standardAnswerArr中 就变为 0,不得分
                for (int i = 0; i < answerArr.length; i++) {
                    if (standardAnswer.indexOf(String.valueOf(answerArr[i])) == -1) {
                        flag = 0;
                    }
                }
                if (flag == 1) {
                    newScore = score / 2;
                }
            } else if (answer.length() == standardAnswer.length()) {
                char[] answerArr = answer.toCharArray();
                // 判断answerArr中的字符是否全在standardAnswerArr中 如果是就得全分
                int flag = 1;// 标志，一旦有一个字符不在standardAnswerArr中 就变为 0,不得分
                for (int i = 0; i < answerArr.length; i++) {
                    if (standardAnswer.indexOf(String.valueOf(answerArr[i])) == -1) {
                        flag = 0;
                    }
                }
                if (flag == 1) {
                    newScore = score;
                }
            }
        }
        return newScore;
    }
}
