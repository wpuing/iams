package com.iams.core.service;

import com.iams.core.dto.result.StudentScantron;
import com.iams.core.dto.student.AssignmentDetails;

/**
 * @author Wei yz
 * @ClassName: CorrectService
 * @Description: 批改业务类
 * @date 2021/5/4 16:57
 */
public interface CorrectService {

    /**
     * 判断学生答案并返回成绩信息
     * @param studentResult 学生答案
     * @param teacherResult 教师答案
     * @param time 作答时间
     * @param score 分数
     * @param type 类型
     * @return
     */
    Float checkResult(String studentResult,String teacherResult,Long time,Float score,Integer type);

    /**
     * 批改作业
     * @param studentScantron 学生答题卡
     * @param assignmentDetails 作业全部信息
     * @return
     */
    Float correct(StudentScantron studentScantron, AssignmentDetails assignmentDetails);
}
