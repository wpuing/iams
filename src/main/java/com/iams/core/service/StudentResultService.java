package com.iams.core.service;

import com.iams.core.dto.student.StudentResultDto;
import com.iams.core.dto.scores.StudentScoresDto;
import com.iams.core.pojo.StudentResult;

import java.util.List;

/**
 * <p>
 *  学生答案关系 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface StudentResultService{

    StudentResult find(Integer id);

    /**
     * 查询该学生的作业的答案
     * @param typeId
     * @param assignmentId
     * @param studentNumber
     * @return
     */
    List<StudentResultDto> selectTopicByNumber(Integer typeId, Integer assignmentId, String studentNumber);

    /**
     * 查询该学生的作业成绩答案
     * @param typeId
     * @param assignmentId
     * @param studentNumber
     * @return
     */
    List<StudentScoresDto> selectScoresByNumber(Integer typeId, Integer assignmentId, String studentNumber);

    int update(StudentResult studentResult);

    int insert(StudentResult studentResult);

    int delete(Integer id);

}
