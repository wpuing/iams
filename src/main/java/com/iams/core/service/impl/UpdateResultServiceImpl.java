package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.iams.common.constant.IamsConstants;
import com.iams.common.util.IamsUtils;
import com.iams.core.dto.assginment.ChoiceDto;
import com.iams.core.dto.assginment.ChoiceDtoImpl;
import com.iams.core.dto.assginment.TopicDto;
import com.iams.core.dto.assginment.TopicDtoImpl;
import com.iams.core.dto.mcq.Choice;
import com.iams.core.dto.scores.AssignmentScoresDetails;
import com.iams.core.dto.scores.ChoiceScoresDto;
import com.iams.core.dto.scores.StudentScoresDto;
import com.iams.core.dto.scores.TopicScoresDto;
import com.iams.core.dto.student.AssignmentStudentDetails;
import com.iams.core.dto.student.StudentResultDto;
import com.iams.core.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wei yz
 * @ClassName: UpdateResultServiceImpl
 * @Description:
 * @date 2021/5/7 11:18
 */
@Service
public class UpdateResultServiceImpl implements UpdateResultService {

    @Autowired
    private SingleService singleService;

    @Autowired
    private MultitermService multitermService;

    @Autowired
    private JudgmentService judgmentService;

    @Autowired
    private CompletionService completionService;

    @Autowired
    private SubjectiveService subjectiveService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private StudentResultService studentResultService;


    @Override
    public AssignmentStudentDetails find(Integer assignmentId, String studentNumber) {
        AssignmentStudentDetails details = new AssignmentStudentDetails();
        //作业信息
        details.setAssignment(assignmentService.find(assignmentId));
        //单选题
        details.setSingleChoiceList(convertChoice(
                IamsUtils.convert(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[0])), IamsConstants.TOPIC_TYPE[0]
                , assignmentId, studentNumber));
        //多选题
        details.setMultipleChoiceList(convertChoice(
                IamsUtils.convert(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[1])), IamsConstants.TOPIC_TYPE[1]
                , assignmentId, studentNumber));
        //判断题
        details.setJudgeTopicList(restsTopic(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[2]), IamsConstants.TOPIC_TYPE[2]
                , assignmentId, studentNumber));
        //填空题
        details.setCompletionTopicList(restsTopic(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[3]), IamsConstants.TOPIC_TYPE[3]
                , assignmentId, studentNumber));
        //主观题
        details.setSubTopicList(restsTopic(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[4]), IamsConstants.TOPIC_TYPE[4]
                , assignmentId, studentNumber));
        IamsUtils.spliceTitle(details);//拼接题目
        return details;
    }

    @Override
    public AssignmentScoresDetails findScores(Integer assignmentId, String studentNumber) {
        AssignmentScoresDetails details = new AssignmentScoresDetails();
        //作业信息
        details.setAssignment(assignmentService.find(assignmentId));
        //单选题
        details.setSingleChoiceList(convertChoiceScores(
                IamsUtils.convert(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[0])), IamsConstants.TOPIC_TYPE[0]
                , assignmentId, studentNumber));
        //多选题
        details.setMultipleChoiceList(convertChoiceScores(
                IamsUtils.convert(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[1])), IamsConstants.TOPIC_TYPE[1]
                , assignmentId, studentNumber));
        //判断题
        details.setJudgeTopicList(restsTopicScores(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[2]), IamsConstants.TOPIC_TYPE[2]
                , assignmentId, studentNumber));
        //填空题
        details.setCompletionTopicList(restsTopicScores(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[3]), IamsConstants.TOPIC_TYPE[3]
                , assignmentId, studentNumber));
        //主观题
        details.setSubTopicList(restsTopicScores(assignmentService.find(assignmentId, IamsConstants.TOPIC_TYPE[4]), IamsConstants.TOPIC_TYPE[4]
                , assignmentId, studentNumber));
        IamsUtils.spliceTitle(details);//拼接题目
        return details;
    }

    /**
     * 回显选择题设置答案
     *
     * @param list          题目信息
     * @param type          类型
     * @param assignmentId  作业号
     * @param studentNumber 学号
     * @return
     */
    private List<ChoiceScoresDto> convertChoiceScores(List<ChoiceDto> list, Integer type, Integer assignmentId, String studentNumber) {
        List<ChoiceScoresDto> chiceDtoList = new ArrayList<>();
        List<StudentScoresDto> resultDtos = studentResultService.selectScoresByNumber(type, assignmentId, studentNumber);//得到关系
        if (!CollectionUtils.isEmpty(resultDtos)) {//存在答案
            for (ChoiceDto choiceDto : list) {//遍历题目选项
                for (StudentScoresDto studentScoresDto : resultDtos) {//遍历查询出来的答案
                    if (choiceDto.getTopicId() == studentScoresDto.getTopicId()) {//是否是当前的题目答案
                        ChoiceScoresDto choiceScoresDto = new ChoiceScoresDto();
                        //设置答案回显
                        List<Choice> choiceList = choiceDto.getMultipleChoice().getChoices();//得到选项集合
                        for (Choice choice : choiceList) {//遍历
                            choice.setIsKey(0);//设置全部不勾选
                            if (type == 1) {//单选题
                                if (choice.getNumber().equals(studentScoresDto.getResult())) {//是这个答案就勾选
                                    choice.setIsKey(1);
                                }
                            }
                            if (type == 2) {//多选题
                                List<String> numbers = IamsUtils.getSubString(studentScoresDto.getResult());//提取答案
                                for (String number : numbers) {//遍历
                                    if (choice.getNumber().equals(number)) {//是这个答案就勾选
                                        choice.setIsKey(1);
                                    }
                                }
                            }
                        }
                        choiceScoresDto.setChoiceDto(choiceDto);//将题目信息添加进去，已经修改过选项
                        choiceScoresDto.setStudentScoresDto(studentScoresDto);//将查询的成绩答案信息查询显示出来
                        chiceDtoList.add(choiceScoresDto);
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(resultDtos)) {//不存在答案
            for (ChoiceDto choiceDto : list) {//遍历题目选项
                chiceDtoList.add(new ChoiceScoresDto().setChoiceDto(choiceDto));
            }
        }
        return chiceDtoList;
    }

    /**
     * 回显判断、填空、主观题 成绩答案信息
     *
     * @param list          题目信息
     * @param type          类型
     * @param assignmentId  作业号
     * @param studentNumber 学号
     * @return
     */
    private List<TopicScoresDto> restsTopicScores(List<TopicDto> list, Integer type, Integer assignmentId, String studentNumber) {
        List<TopicScoresDto> topicDtos = new ArrayList<>();
        List<StudentScoresDto> resultDtos = studentResultService.selectScoresByNumber(type, assignmentId, studentNumber);//得到关系
        if (!CollectionUtils.isEmpty(resultDtos)) {//存在答案
            for (TopicDto topicDto : list) {//遍历题目选项
                for (StudentScoresDto studentScoresDto : resultDtos) {//遍历查询出来的答案
                    if (topicDto.getTopicId() == studentScoresDto.getTopicId()) {//是否是当前的题目答案
                        TopicScoresDto dto = new TopicScoresDto();
                        dto.setTopicDto(topicDto);
                        dto.setStudentScoresDto(studentScoresDto);
                        topicDtos.add(dto);
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(resultDtos)) {//不存在答案
            for (TopicDto topicDto : list) {//遍历题目选项
                topicDtos.add(new TopicScoresDto().setTopicDto(topicDto));
            }
        }
        return topicDtos;
    }

    /**
     * 回显选择题设置答案
     *
     * @param list          题目信息
     * @param type          类型
     * @param assignmentId  作业号
     * @param studentNumber 学号
     * @return
     */
    private List<ChoiceDtoImpl> convertChoice(List<ChoiceDto> list, Integer type, Integer assignmentId, String studentNumber) {
        List<ChoiceDtoImpl> chiceDtoList = new ArrayList<>();
        List<StudentResultDto> resultDtos = studentResultService.selectTopicByNumber(type, assignmentId, studentNumber);//得到关系
        if (!CollectionUtils.isEmpty(resultDtos)) {//存在答案
            for (ChoiceDto choiceDto : list) {//遍历题目选项
                for (StudentResultDto studentResultDto : resultDtos) {//遍历查询出来的答案
                    if (choiceDto.getTopicId() == studentResultDto.getOsTopicId()) {//是否是当前的题目答案
                        ChoiceDtoImpl choiceDtoImpl = new ChoiceDtoImpl();
                        //设置答案回显
                        List<Choice> choiceList = choiceDto.getMultipleChoice().getChoices();//得到选项集合
                        for (Choice choice : choiceList) {//遍历
                            choice.setIsKey(0);//设置全部不勾选
                            if (type == 1) {//单选题
                                if (choice.getNumber().equals(studentResultDto.getResult())) {//是这个答案就勾选
                                    choice.setIsKey(1);
                                }
                            }
                            if (type == 2) {//多选题
                                List<String> numbers = IamsUtils.getSubString(studentResultDto.getResult());//提取答案
                                for (String number : numbers) {//遍历
                                    if (choice.getNumber().equals(number)) {//是这个答案就勾选
                                        choice.setIsKey(1);
                                    }
                                }
                            }
                        }
                        choiceDto.setScore(studentResultDto.getScore())
                                .setResult(studentResultDto.getResult());
                        BeanUtils.copyProperties(choiceDto, choiceDtoImpl);//拷贝
                        choiceDtoImpl.setStudentResultId(studentResultDto.getStudentResultId())
                                .setChildId(studentResultDto.getTopicId());
                        chiceDtoList.add(choiceDtoImpl);
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(resultDtos)) {//不存在答案
            for (ChoiceDto choiceDto : list) {//遍历题目选项
                ChoiceDtoImpl choiceDtoImpl = new ChoiceDtoImpl();
                BeanUtils.copyProperties(choiceDto, choiceDtoImpl);//拷贝
                chiceDtoList.add(choiceDtoImpl);
            }
        }
        return chiceDtoList;
    }

    /**
     * 回显判断、填空、主观题
     *
     * @param list          题目信息
     * @param type          类型
     * @param assignmentId  作业号
     * @param studentNumber 学号
     * @return
     */
    private List<TopicDtoImpl> restsTopic(List<TopicDto> list, Integer type, Integer assignmentId, String studentNumber) {
        List<TopicDtoImpl> topicDtos = new ArrayList<>();
        List<StudentResultDto> resultDtos = studentResultService.selectTopicByNumber(type, assignmentId, studentNumber);//得到关系
        if (!CollectionUtils.isEmpty(resultDtos)) {//存在答案
            for (TopicDto topicDto : list) {//遍历题目选项
                for (StudentResultDto studentResultDto : resultDtos) {//遍历查询出来的答案
                    if (topicDto.getTopicId() == studentResultDto.getOsTopicId()) {//是否是当前的题目答案
                        TopicDtoImpl dto = new TopicDtoImpl();
                        //设置答案回显
                        topicDto.setScore(studentResultDto.getScore())
                                .setResult(studentResultDto.getResult());
                        BeanUtils.copyProperties(topicDto, dto);//拷贝
                        dto.setStudentResultId(studentResultDto.getStudentResultId())
                                .setChildId(studentResultDto.getTopicId());
                        topicDtos.add(dto);
                    }
                }
            }
        }
        if (CollectionUtils.isEmpty(resultDtos)) {//不存在答案
            for (TopicDto topicDto : list) {//遍历题目选项
                TopicDtoImpl dto = new TopicDtoImpl();
                BeanUtils.copyProperties(topicDto, dto);//拷贝
                topicDtos.add(dto);
            }
        }
        return topicDtos;
    }
}
