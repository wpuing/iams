package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iams.common.util.Utils;
import com.iams.core.dto.assginment.AssignmentParameters;
import com.iams.core.dto.assginment.TopicParameters;
import com.iams.core.mapper.AssignmentTopicMapper;
import com.iams.core.mapper.ScoresMapper;
import com.iams.core.mapper.StudentResultMapper;
import com.iams.core.mapper.StudentTaskMapper;
import com.iams.core.pojo.Scores;
import com.iams.core.pojo.StudentResult;
import com.iams.core.pojo.StudentTask;
import com.iams.core.service.AssignmentScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Wei yz
 * @ClassName: AssignmentScoresServiceImpl
 * @Description: 作业成绩详情
 * @date 2021/5/25 21:33
 */

@Service
public class AssignmentScoresServiceImpl implements AssignmentScoresService {

    @Autowired
    private ScoresMapper scoresMapper;

    @Autowired
    private StudentTaskMapper studentTaskMapper;

    @Autowired
    private AssignmentTopicMapper assignmentTopicMapper;

    @Autowired
    private StudentResultMapper studentResultMapper;


    @Override
    public AssignmentParameters find(Integer assignmentId) {
        AssignmentParameters parameters = new AssignmentParameters();
        parameters.setAssignmentId(assignmentId);
        //查询最大值最小值
        List<Float> list = findAssignmentScores(assignmentId);//得到作业全部分数
        parameters.setTopScore(getMaxOrMin(list, true));
        parameters.setLowestScore(getMaxOrMin(list, false));
        //查询平均分和人数
        Integer population = selPopulation(assignmentId);//得出总人数
        parameters.setPopulation(population);//添加到总人数
        parameters.setAverage(getAverage(list, population));//设置平均分
        //查询题目的详细参数
        parameters.setParametersList(selTopicParameters(assignmentId, population));
        return parameters;
    }

    /**
     * 查询该作业的人数
     *
     * @param assignmentId 作业id
     * @return
     */
    private Integer selPopulation(Integer assignmentId) {
        LambdaQueryWrapper<StudentTask> wrapper = Wrappers.<StudentTask>lambdaQuery();
        wrapper.eq(StudentTask::getAssignmentId, assignmentId);
        Integer count = studentTaskMapper.selectCount(wrapper);
        return count;
    }

    /**
     * 查询题目的详细参数
     *
     * @param assignmentId 作业编号
     * @param population   作业总人数
     * @return
     */
    private List<TopicParameters> selTopicParameters(Integer assignmentId, Integer population) {
        //1.查询该作业的所有题目
        List<TopicParameters> parametersList = findTopicList(assignmentId);
        //2.查询该题目的最低、高分、平均分
        //2.1不为空时则循环集合
        if (!CollectionUtils.isEmpty(parametersList)) {
            for (TopicParameters tp : parametersList) {
                //查询该题目的所有成绩
                List<Float> scoreList = findTopicScores(assignmentId, tp.getTypeId(), tp.getTopicId());
                //设置最大最小值、平均分、答题人数
                tp.setTopScore(getMaxOrMin(scoreList, true));
                tp.setLowestScore(getMaxOrMin(scoreList, false));
                tp.setAverage(getAverage(scoreList, population));
                tp.setPopulation(scoreList.size());
            }
        }
        return parametersList;
    }

    /**
     * 查询该作业的该题目的所有成绩
     *
     * @param assignmentId 作业id
     * @param typeId       类型id
     * @param topicId      题目id
     * @return
     */
    private List<Float> findTopicScores(Integer assignmentId, Integer typeId, Integer topicId) {
        LambdaQueryWrapper<StudentResult> wrapper = Wrappers.<StudentResult>lambdaQuery();
        wrapper.eq(StudentResult::getAssignmentId, assignmentId)
                .eq(StudentResult::getOsTopicId, topicId)
                .eq(StudentResult::getTypeId, typeId);
        List<StudentResult> resultList = studentResultMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(resultList)) {//不为空则提取
            List<Float> list = resultList.stream().map(StudentResult::getScore).collect(Collectors.toList());
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 查询该作业的全部成绩
     *
     * @param assignmentId
     * @return
     */
    private List<Float> findAssignmentScores(Integer assignmentId) {
        LambdaQueryWrapper<Scores> wrapper = Wrappers.<Scores>lambdaQuery();
        wrapper.eq(Scores::getAssignmentId, assignmentId);
        List<Scores> resultList = scoresMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(resultList)) {//不为空则提取
            List<Float> list = resultList.stream().map(Scores::getScore).collect(Collectors.toList());
            return list;
        }
        return new ArrayList<>();
    }

    /**
     * 查询该作业的所有题目
     *
     * @param assignmentId
     * @return
     */
    private List<TopicParameters> findTopicList(Integer assignmentId) {
        List<TopicParameters> parametersList = new ArrayList<>();
        Integer index = 0;
        for (int i = 4; i < 6; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("typeId", i);
            map.put("assignmentId", assignmentId);
            List<TopicParameters> list = assignmentTopicMapper.findTopicDetails(map);
            //排序
            Collections.sort(list, new Comparator<TopicParameters>() {
                @Override
                public int compare(TopicParameters o1, TopicParameters o2) {
                    Integer diff = o1.getTypeId() - o2.getTypeId();
                    if (diff > 0) {
                        return 1;
                    } else if (diff < 0) {
                        return -1;
                    }
                    return 0;
                }
            });
            for (TopicParameters topicParameters : list) {
                index++;
                topicParameters.setIndex(index);
            }
            index = 0;
            parametersList.addAll(list);
        }
        return parametersList;
    }

    /**
     * 提取最大最小值
     *
     * @param list 分数集合
     * @param flag true 为最大值 false为最小值
     * @return
     */
    private Float getMaxOrMin(List<Float> list, boolean flag) {
        Float max = 0.0f;//将数组的第一个元素赋给max
        Float min = 0.0f;//将数组的第一个元素赋给min
        if (!CollectionUtils.isEmpty(list)) {
            max = list.get(0);
            min = list.get(0);
            for (int i = 1; i < list.size(); i++) {//从数组的第二个元素开始赋值，依次比较
                if (list.get(i) > max) {//如果list[i]大于最大值，就将list[i]赋给最大值
                    max = list.get(i);
                }
                if (list.get(i) < min) {//如果list[i]小于最小值，就将list[i]赋给最小值
                    min = list.get(i);
                }
            }
        }
        if (flag) return max;
        return min;
    }

    /**
     * 计算平均分
     *
     * @param list 分数集合
     * @return
     */
    private Float getAverage(List<Float> list, Integer number) {
        Float totalScore = new Float(0.0f);
        if (!CollectionUtils.isEmpty(list)) {
            if (!Utils.isEmpty(number)) {
                number = list.size();
            }
            for (Float score : list) {
                totalScore += score;
            }
            return totalScore / number;
        }
        return totalScore;
    }
}

