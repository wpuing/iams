package com.iams.core.service;

import com.iams.core.pojo.Scores;

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
