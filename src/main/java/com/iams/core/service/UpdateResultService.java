package com.iams.core.service;


import com.iams.core.dto.scores.AssignmentScoresDetails;
import com.iams.core.dto.student.AssignmentStudentDetails;

/**
 *  @author: Wei yz
 *  @Date: 2021/5/7 11:17
 *  @Description: 更新答案业务
 */
public interface UpdateResultService {

    /**
     * 查询某学生的某次作业详细信息
     * @param assignmentId
     * @param studentNumber
     * @return
     */
    AssignmentStudentDetails find(Integer assignmentId,String studentNumber);

    /**
     * 查询某学生的某次作业成绩信息
     * @param assignmentId
     * @param studentNumber
     * @return
     */
    AssignmentScoresDetails findScores(Integer assignmentId, String studentNumber);




}
