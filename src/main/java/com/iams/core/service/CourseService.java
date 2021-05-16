package com.iams.core.service;

import com.iams.common.util.LayResult;
import com.iams.core.dto.CourseDto;
import com.iams.core.pojo.Course;

import java.util.List;

/**
 * <p>
 *   课程 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface CourseService{

    CourseDto find(Integer id);

    Course find(String number);

    /**
     * 查询该教师所有课程
     * @param ids 教师所有的课程编号
     * @param pageNum 当前页
     * @param pageSize 每页数
     * @return
     */
    LayResult find(List<String> ids, Integer pageNum, Integer pageSize);


    /**
     * 查询该教师所有课程
     * @param teacherId 教师编号
     * @param pageNum 当前页
     * @param pageSize 每页数
     * @return
     */
    LayResult findTeacherCourseList(String teacherId, Integer pageNum, Integer pageSize);

    /**
     * 查询所有课程
     * @param condition 条件
     * @param pageNum 当前页
     * @param pageSize 每页数
     * @return
     */
    LayResult find(String condition, Integer pageNum, Integer pageSize);

    /**
     * 查询该课程的所有学生
     * @param condition 条件
     * @param pageNum 当前页
     * @param pageSize 每页数
     * @return
     */
    LayResult find(String condition,String courseId, Integer pageNum, Integer pageSize);


    /**
     * 查询该学生的所有课程
     * @param condition 条件
     * @param studentId 学生编号
     * @param pageNum 当前页
     * @param pageSize 每页数
     * @return
     */
    LayResult findCourseByStudentId(String condition,String studentId, Integer pageNum, Integer pageSize);

    int insert(Course course);

    int update(Course course);

    int delete(Integer id);


}
