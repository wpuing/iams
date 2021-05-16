package com.iams.core.service;

import com.iams.common.util.LayResult;
import com.iams.core.pojo.Student;

import java.util.List;

/**
 * <p>
 *   学生 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface StudentService{

    /**
     * 查询单个学生信息
     * @param id  学生id
     * @return  没有密码等附属信息
     */
    Student find(Integer id);



    /**
     * 条件分页查询学生列表
     * @param condition  条件，包括名称、邮箱、学号、性别、手机号、专业、学院
     * @param startTime  创建开始时间
     * @param endTime  创建结束时间
     * @param pageNum  当前页
     * @param pageSize  每页数
     * @return 未带页参数学生列表信息
     */
    LayResult find(String condition, String startTime, String endTime, Integer pageNum, Integer pageSize);

    /**
     * 根据编号或者邮箱
     * @param condition 姓名或者邮箱
     * @return
     */
    List<Student> find(String condition);


    /**
     * 添加学生信息
     * @param student 学生信息
     * @return 1成功
     */
    int insert(Student student);


    /**
     * 修改学生信息
     * @param student 学生信息
     * @param flag true 忽略密码
     * @return 1成功
     */
    int update(Student student,boolean flag);


    /**
     * 删除单个学生
     * @param id id主键
     * @return 1成功
     */
    int delete(Integer id);


}
