package com.iams.core.service;

import com.iams.common.util.LayResult;
import com.iams.core.dto.scores.StudentScoresDetails;
import com.iams.core.pojo.Scores;

import java.util.List;

/**
 * <p>
 *   成绩 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface ScoresService{

    Scores find(Integer id);

    Scores find(String studentId,Integer assignmentId);

    /**
     * 根据条件查询成绩信息
     * @param assignmentId 作业号
     * @param studentId 学号
     * @param pageNum 当前页
     * @param pageSize 每页数
     * @return
     */
    LayResult find(Integer assignmentId,String studentId,Integer pageNum,Integer pageSize);

    /**
     * 查询该学生的作业成绩明细答案
     * @param assignmentId 作业号
     * @param studentNumber 学号
     * @return
     */
    List<StudentScoresDetails> selectScoresByNumber(Integer assignmentId, String studentNumber);


    int update(Scores scores);

    /**
     * 根据作业号+学号修改成绩信息
     * @param scores
     * @return
     */
    int updateByNumber(Scores scores);

    int insert(Scores scores);

    int delete(Integer id);

}
