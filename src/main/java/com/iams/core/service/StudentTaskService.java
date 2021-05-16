package com.iams.core.service;

import com.iams.core.pojo.StudentTask;

import java.util.List;

/**
 * <p>
 *  学生作业关系 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface StudentTaskService {

    StudentTask findById(Integer id);

    /**
     * 根据作业id查询所有的关系
     * @param assignmentId 作业id
     * @return
     */
    List<StudentTask> find(Integer assignmentId);

    int insert(StudentTask studentTask);

    /**
     * 批量添加作业学生关系，默认未发布
     * @param numbers 学号集合
     * @param assignmentId 作业号
     * @return
     */
    int insert(List<String> numbers,Integer assignmentId);

    int update(StudentTask studentTask);

    /**
     * 修改该作业的所有答题限制
     * @param isAnswer 是否允许答题 0允许，1不允许
     * @param assignmentId 作业id
     * @return
     */
    int update(Integer isAnswer,Integer assignmentId);

    /**
     * 根据学号和作业号修改信息
     * @param studentResult
     * @return
     */
    int updateByNumber(StudentTask studentResult);

    int delete(Integer id);

}
