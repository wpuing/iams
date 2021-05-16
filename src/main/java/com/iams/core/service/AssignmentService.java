package com.iams.core.service;

import com.iams.common.util.LayResult;
import com.iams.core.dto.assginment.TopicDto;
import com.iams.core.dto.student.StudentTaskDto;
import com.iams.core.pojo.Assignment;

import java.util.List;

/**
 * <p>
 *   作业 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface AssignmentService{


    Assignment find(Integer id);

    /**
     * 根据学号和课程号查询该学生的该课程所有的作业
     * @param courseNumber 课程号
     * @param studentNumber 学号
     * @param flag true 待完成 false 已完成
     * @return
     */
    List<StudentTaskDto> find(String courseNumber, String studentNumber,boolean flag);

    /**
     * 条件查询
     * @param condition 标题、描述
     * @param courseId 课程号
     * @param teacherId 教师号
     * @param startTime 创建时间开始
     * @param endTime 创建时间结束
     * @param pageNum 当前页
     * @param pageSize 每页数
     * @return
     */
    LayResult find(String condition,String courseId,String teacherId,String startTime, String endTime, Integer pageNum, Integer pageSize);

    /**
     * 查询题目
     * @param id  作业id
     * @param typeId 类型id
     * @param type true为客观题，false为主观题
     * @return
     */
    List<TopicDto> find(Integer id,Integer typeId,boolean type);

    /**
     * 查询题目
     * @param id  作业id
     * @param type 1单选题 2多选题 3判断题 4填空题 5主观题
     * @return
     */
    List<TopicDto> find(Integer id,int type);

    /**
     * 查询作业总分数
     * @param assignmentId 作业号
     * @param typeId 主观题类型id
     * @param type true客观题，false主观题
     * @return
     */
    Float findScore(Integer assignmentId, Integer typeId,boolean type);

    int insert(Assignment assignment);

    int update(Assignment assignment);

    int delete(Integer id);

}
