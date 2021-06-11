package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.IamsUtils;
import com.iams.common.util.LayResult;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.EmployeeDto;
import com.iams.core.mapper.EmployeeMapper;
import com.iams.core.pojo.Employee;
import com.iams.core.pojo.RoleEnum;
import com.iams.core.service.BaseService;
import com.iams.core.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  员工 服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-01
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;//注入员工mapper



    @Override
    public EmployeeDto find(Integer id) {
        Employee employee = select(id);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setRoleName(RoleEnum.getRole(employee.getRoleId()).getName());//设置角色名
        BeanUtils.copyProperties(employee, employeeDto);//拷贝
        return employeeDto;
    }

    @Override
    public Employee findById(Integer id) {
        return select(id);
    }


    @Override
    public LayResult find(String condition, Integer roleId, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        BaseService.checkPage(pageNum,pageSize);//检查分页参数
        LambdaQueryWrapper<Employee> wrapper = Wrappers.<Employee>lambdaQuery();
        Page<Employee> page = new Page<>(pageNum,pageSize,true);
        //创建日期
        BaseService.dateRange(wrapper,startTime,endTime);
        //模糊条件
        if(Utils.isEmpty(condition)){
            wrapper.like(Employee::getNumber,condition)
                    .or()
                    .like(Employee::getName,condition)
                    .or()
                    .like(Employee::getEmail,condition)
                    .or()
                    .like(Employee::getPhone,condition)
                    .or()
                    .like(Employee::getSex,condition);
        }
        //角色
        if(Utils.isEmpty(roleId)){
            wrapper.eq(Employee::getRoleId,roleId);
        }
        IPage<Employee> iPage = employeeMapper.selectPage(page,wrapper);
        List<Employee> employeeList = iPage.getRecords();//数据
        return ResultGenerator.getData(convert(employeeList),iPage.getTotal());
    }

    @Override
    public List<Employee> find(String condition,Integer type) {
        LambdaQueryWrapper<Employee> wrapper = Wrappers.<Employee>lambdaQuery();
        wrapper.eq(Employee::getRoleId,type);
        wrapper.eq(Employee::getNumber,condition)
                .or()
                .eq(Employee::getEmail,condition);
        List<Employee> employeeList = employeeMapper.selectList(wrapper);
        return employeeList;
    }

    /**
     * 转换
     * @param employeeList
     * @return
     */
    private List<EmployeeDto> convert(List<Employee> employeeList) {
        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(employeeList)){
            for (Employee employee:employeeList) {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setRoleName(RoleEnum.getRole(employee.getRoleId()).getName());
                BeanUtils.copyProperties(employee, employeeDto);//拷贝
                employeeDtoList.add(employeeDto);
            }
        }
        return employeeDtoList;
    }


    @Override
    public int insert(Employee employee) {
        employee.setId(null);
        employee.setDeleted(IamsConstants.DELETED);//逻辑删除
        updateEncryptedInfo(employee);//更新加密信息，密码、盐、随机码
        checkEmployeeInfo(employee);//检查重复邮箱和员工号和姓名
        return employeeMapper.insert(employee);
    }


    @Override
    public int update(Employee employee,boolean flag) {
        select(employee.getId());//检查是否存在
        if(!flag){
            updateEncryptedInfo(employee);//更新加密信息，密码、盐、随机码
        }
        checkEmployeeInfo(employee);//检查重复邮箱和员工号和姓名，排除当前id的记录
        return employeeMapper.updateById(employee);
    }


    @Override
    public int delete(Integer id) {
        select(id);//检验
        return employeeMapper.deleteById(id);
    }


    /**
     * 检查添加的用户是否包含重复的名称、邮箱、编号
     * @param employee 员工信息
     */
    private void checkEmployeeInfo(Employee employee){
        LambdaQueryWrapper<Employee> wrapper = Wrappers.<Employee>lambdaQuery();
        if(Utils.isEmpty(employee.getId())){//id不为空且小于等于0则为修改
            wrapper.ne(Employee::getId,employee.getId());//不等于当前id，排除出来
        }
        wrapper.gt(Employee::getId,"0");//用于拼接SQL语句
        wrapper.and(w->w.eq(Employee::getName,employee.getName())
                .or()
                .eq(Employee::getEmail,employee.getEmail())
                .or()
                .eq(Employee::getNumber,employee.getNumber()));
        List<Employee> list = employeeMapper.selectList(wrapper);
        if(list!=null && list.size()>0){//存在则取第一条记录的id
            throw new ParameterException("更新失败，更新的信息中已存在该数据，id："+list.get(0).getId());
        }
    }


    /**
     * 根据id查询员工信息，不存在则抛出异常
     * @param id  员工id
     * @return 该id的员工信息
     */
    private Employee select(Integer id){
        Utils.isEmpty(id,"查询的员工id不能小于等于0或为空！");
        Employee employee = employeeMapper.selectById(id);
        if(employee==null){
            throw new ParameterException("查询失败，数据为空！");
        }
       return employee;

    }


    /**
     * 更新加密信息
     * @param employee  员工信息
     */
    private void updateEncryptedInfo(Employee employee){
        String salt = IamsUtils.getRandomIdByUtil();//16位
        String randomCode = IamsUtils.generateShortUuid();//8位
        employee.setPassword(IamsUtils.getMD5Value(employee.getPassword(),salt));//加密后的密码
        employee.setRandomId(randomCode);//随机码
        employee.setSalt(salt);//盐值
    }


}
