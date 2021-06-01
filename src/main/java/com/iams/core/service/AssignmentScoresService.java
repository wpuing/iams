package com.iams.core.service;


import com.iams.core.dto.assginment.AssignmentParameters;

/**
 *  @author: Wei yz
 *  @Date: 2021/5/25 21:31
 *  @Description: 作业参数业务类
 */
public interface AssignmentScoresService {

    /**
     * 查询作业的成绩情况
     * @param assignmentId 作业编号
     * @return
     */
    AssignmentParameters find(Integer assignmentId);

}
