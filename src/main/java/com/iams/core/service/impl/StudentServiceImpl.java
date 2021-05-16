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
import com.iams.core.dto.StudentDto;
import com.iams.core.mapper.StudentMapper;
import com.iams.core.pojo.Student;
import com.iams.core.service.BaseService;
import com.iams.core.service.StudentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;


    @Override
    public Student find(Integer id) {
        Student student = select(id);
        return student;
    }

    @Override
    public LayResult find(String condition, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        BaseService.checkPage(pageNum, pageSize);//检查分页参数
        LambdaQueryWrapper<Student> wrapper = Wrappers.<Student>lambdaQuery();
        Page<Student> page = new Page<>(pageNum, pageSize, true);
        BaseService.dateRange(wrapper, startTime, endTime);
        //模糊条件
        if (Utils.isEmpty(condition)) {
            wrapper.like(Student::getNumber, condition)
                    .or()
                    .like(Student::getName, condition)
                    .or()
                    .like(Student::getEmail, condition)
                    .or()
                    .like(Student::getInstitute, condition)
                    .or()
                    .like(Student::getProfessional, condition)
                    .or()
                    .like(Student::getPhone, condition)
                    .or()
                    .like(Student::getSex, condition);
        }
        IPage<Student> iPage = studentMapper.selectPage(page, wrapper);
        List<Student> studentList = iPage.getRecords();
        return ResultGenerator.getData(convert(studentList), iPage.getTotal());
    }

    @Override
    public List<Student> find(String condition) {
        LambdaQueryWrapper<Student> wrapper = Wrappers.<Student>lambdaQuery();
        wrapper.eq(Student::getNumber, condition)
                .or()
                .eq(Student::getEmail, condition);
        List<Student> studentList = studentMapper.selectList(wrapper);
        return studentList;
    }

    @Override
    public int insert(Student student) {
        student.setId(null);
        student.setRoleId(1);
        if (!Utils.isEmpty(student.getDeleted())) {
            student.setDeleted(IamsConstants.DELETED);//逻辑删除
        }
        updateEncryptedInfo(student);//更新加密信息，密码、盐、随机码
        checkEmployeeInfo(student);//检查重复邮箱和学号
        return studentMapper.insert(student);
    }

    @Override
    public int update(Student student,boolean flag) {
        select(student.getId());//检查是否存在
        if(!flag){
            updateEncryptedInfo(student);//更新加密信息，密码、盐、随机码
        }
        checkEmployeeInfo(student);//检查重复邮箱和学号，排除当前id的记录
        return studentMapper.updateById(student);
    }

    @Override
    public int delete(Integer id) {
        select(id);//检验
        return studentMapper.deleteById(id);
    }


    /**
     * 检查添加的用户是否包含重复的名称、邮箱、编号
     *
     * @param student 学生信息
     */
    private void checkEmployeeInfo(Student student) {
        LambdaQueryWrapper<Student> wrapper = Wrappers.<Student>lambdaQuery();
        if (Utils.isEmpty(student.getId())) {//id不为空且小于等于0则为修改
            wrapper.ne(Student::getId, student.getId());//不等于当前id，排除出来
        }
        wrapper.gt(Student::getId, "0");//用于拼接SQL语句
        wrapper.and(
                w -> w.eq(Student::getEmail, student.getEmail())
                        .or()
                        .eq(Student::getNumber, student.getNumber()));
        List<Student> list = studentMapper.selectList(wrapper);
        if (list != null && list.size() > 0) {//存在则取第一条记录的id
            throw new ParameterException("更新失败，更新的信息中已存在该数据，id：" + list.get(0).getId());
        }
    }


    /**
     * 根据id查询学生信息，不存在则抛出异常
     *
     * @param id 学生id
     * @return 该id的学生信息
     */
    private Student select(Integer id) {
        Utils.isEmpty(id, "查询的员工id不能小于等于0或为空！");
        Student student = studentMapper.selectById(id);
        if (student == null) {
            throw new ParameterException("查询失败，数据为空！");
        }
        return student;

    }


    /**
     * 更新加密信息
     *
     * @param student 学生信息
     */
    private void updateEncryptedInfo(Student student) {
        String salt = IamsUtils.getRandomIdByUtil();//16位
        String randomCode = IamsUtils.generateShortUuid();//8位
        student.setPassword(IamsUtils.getMD5Value(student.getPassword(), salt));//加密后的密码
        student.setRandomId(randomCode);//随机码
        student.setSalt(salt);//盐值
    }

    /**
     * 转换
     *
     * @param studentList
     * @return
     */
    private List<StudentDto> convert(List<Student> studentList) {
        List<StudentDto> studentDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(studentList)) {
            for (Student student : studentList) {
                StudentDto studentDto = new StudentDto();
                BeanUtils.copyProperties(student, studentDto);//拷贝
                studentDtoList.add(studentDto);
            }
        }
        return studentDtoList;
    }

}
