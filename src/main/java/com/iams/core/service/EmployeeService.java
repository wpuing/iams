package com.iams.core.service;

import com.iams.common.util.LayResult;
import com.iams.core.dto.EmployeeDto;
import com.iams.core.pojo.Employee;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-01
 */
public interface EmployeeService{

    /**
     * 查询单个员工信息
     * @param id  员工id
     * @return  没有密码等附属信息
     */
    EmployeeDto find(Integer id);

    /**
     * 查询单个员工信息
     * @param id  员工id
     * @return  含密码等附属信息
     */
    Employee findById(Integer id);


    /**
     * 条件分页查询员工列表
     * @param condition  条件，包括名称、邮箱、编号、性别、手机号
     * @param roleId  角色号
     * @param startTime  创建开始时间
     * @param endTime  创建结束时间
     * @param pageNum  当前页
     * @param pageSize  每页数
     * @return layui分页格式数据
     */
   LayResult find(String condition, Integer roleId, String startTime, String endTime, Integer pageNum, Integer pageSize);


    List<Employee> find(String condition,Integer type);

    /**
     * 添加员工信息
     * @param employee 员工信息
     * @return 1成功
     */
    int insert(Employee employee);


    /**
     * 修改员工信息
     * @param employee 员工信息
     * @param flag true 忽略密码
     * @return 1成功
     */
    int update(Employee employee,boolean flag);


    /**
     * 删除单个员工
     * @param id id主键
     * @return 1成功
     */
    int delete(Integer id);
}
