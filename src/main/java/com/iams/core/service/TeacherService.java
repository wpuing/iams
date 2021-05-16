package com.iams.core.service;

import com.iams.common.util.LayResult;
import com.iams.core.pojo.Teacher;

import java.util.List;

/**
 * <p>
 * 教师 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface TeacherService {

    /**
     * 查询单个教师的信息
     * @param id 教师id
     * @return
     */
    Teacher find(Integer id);

    /**
     * 查询单个学生信息,状态是1
     * @param id  学生id
     * @return
     */
    Teacher findById(Integer id);

    /**
     * 分页查询教师信息
     * @param condition 条件，名称、邮箱、编号、学院、手机号、性别、答疑信息
     * @param startTime 创建开始日期
     * @param endTime 创建结束日期
     * @param pageNum  当前页
     * @param pageSize 每页数
     * @return
     */
    LayResult find(String condition, String startTime, String endTime, Integer pageNum, Integer pageSize);


    List<Teacher> find(String condition);

    /**
     * 添加教师信息
     * @param teacher 教师信息
     * @return
     */
    int insert(Teacher teacher);

    /**
     * 修改教师信息
     * @param teacher 教师信息
     * @param flag true 忽略密码
     * @return
     */
    int update(Teacher teacher,boolean flag);

    /**
     * 根据id删除教师信息
     * @param id  教师id
     * @return
     */
    int delete(Integer id);



}
