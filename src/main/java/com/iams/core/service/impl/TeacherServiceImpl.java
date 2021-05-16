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
import com.iams.core.dto.TeacherDto;
import com.iams.core.mapper.TeacherMapper;
import com.iams.core.pojo.Teacher;
import com.iams.core.service.BaseService;
import com.iams.core.service.TeacherService;
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
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher find(Integer id) {
        Teacher teacher = select(id);//得到信息
        return teacher;
    }

    @Override
    public Teacher findById(Integer id) {
        return teacherMapper.selById(id);
    }

    @Override
    public LayResult find(String condition, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        BaseService.checkPage(pageNum, pageSize);//检查分页参数
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.<Teacher>lambdaQuery();
        Page<Teacher> page = new Page<>(pageNum, pageSize, true);
        //创建日期
        BaseService.dateRange(wrapper, startTime, endTime);
        //模糊条件
        if (Utils.isEmpty(condition)) {
            wrapper.like(Teacher::getNumber, condition)
                    .or()
                    .like(Teacher::getName, condition)
                    .or()
                    .like(Teacher::getEmail, condition)
                    .or()
                    .like(Teacher::getPhone, condition)
                    .or()
                    .like(Teacher::getSex, condition)
                    .or()
                    .like(Teacher::getInstitute, condition)
                    .or()
                    .like(Teacher::getAnswerInfo, condition);
        }
        IPage<Teacher> iPage = teacherMapper.selectPage(page, wrapper);
        List<Teacher> teacherList = iPage.getRecords();
        return ResultGenerator.getData(convert(teacherList), iPage.getTotal());
    }

    @Override
    public List<Teacher> find(String condition) {
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.<Teacher>lambdaQuery();
        wrapper.eq(Teacher::getNumber, condition)
                .or()
                .eq(Teacher::getEmail, condition);
        List<Teacher> teacherList = teacherMapper.selectList(wrapper);
        return teacherList;
    }

    /**
     * 转换
     *
     * @param teacherList
     * @return
     */
    private List<TeacherDto> convert(List<Teacher> teacherList) {
        List<TeacherDto> teacherDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(teacherList)) {
            for (Teacher teacher : teacherList) {
                TeacherDto teacherDto = new TeacherDto();
                BeanUtils.copyProperties(teacher, teacherDto);//拷贝
                teacherDtoList.add(teacherDto);
            }
        }
        return teacherDtoList;
    }

    @Override
    public int insert(Teacher teacher) {
        teacher.setRoleId(2);
        teacher.setId(null);
        if (!Utils.isEmpty(teacher.getDeleted())) {//id为空或小于等于0
            teacher.setDeleted(IamsConstants.DELETED);//逻辑删除
        }
        updateEncryptedInfo(teacher);//更新加密信息，密码、盐、随机码
        checkEmployeeInfo(teacher);//检查重复邮箱和教师号和姓名
        return teacherMapper.insert(teacher);
    }

    @Override
    public int update(Teacher teacher,boolean flag) {
        select(teacher.getId());//检查是否存在
        if(!flag){
            updateEncryptedInfo(teacher);//更新加密信息，密码、盐、随机码
        }
        checkEmployeeInfo(teacher);//检查重复邮箱和教师号和姓名，排除当前id的记录
        return teacherMapper.updateById(teacher);
    }

    @Override
    public int delete(Integer id) {
        select(id);//检验
        return teacherMapper.deleteById(id);
    }


    /**
     * 检查添加的用户是否包含重复的名称、邮箱、编号
     *
     * @param teacher 员工信息
     */
    private void checkEmployeeInfo(Teacher teacher) {
        LambdaQueryWrapper<Teacher> wrapper = Wrappers.<Teacher>lambdaQuery();
        if (Utils.isEmpty(teacher.getId())) {//id不为空且小于等于0则为修改
            wrapper.ne(Teacher::getId, teacher.getId());//不等于当前id，排除出来
        }
        wrapper.gt(Teacher::getId, "0");//用于拼接SQL语句
        wrapper.and(w -> w.eq(Teacher::getName, teacher.getName())
                .or()
                .eq(Teacher::getEmail, teacher.getEmail())
                .or()
                .eq(Teacher::getNumber, teacher.getNumber()));
        List<Teacher> list = teacherMapper.selectList(wrapper);
        if (list != null && list.size() > 0) {//存在则取第一条记录的id
            throw new ParameterException("更新失败，更新的信息中已存在该数据，id：" + list.get(0).getId());
        }
    }

    /**
     * 根据id查询教师信息，不存在则抛出异常
     *
     * @param id 教师id
     * @return 该id的教师信息
     */
    private Teacher select(Integer id) {
        Utils.isEmpty(id, "查询的教师id不能小于等于0或为空！");
        Teacher teacher = teacherMapper.selectById(id);
        if (teacher == null) {
            throw new ParameterException("查询失败，数据为空！");
        }
        return teacher;

    }

    /**
     * 更新加密信息
     *
     * @param teacher 教师信息
     */
    private void updateEncryptedInfo(Teacher teacher) {
        String salt = IamsUtils.getRandomIdByUtil();//16位
        String randomCode = IamsUtils.generateShortUuid();//8位
        teacher.setPassword(IamsUtils.getMD5Value(teacher.getPassword(), salt));//加密后的密码
        teacher.setRandomId(randomCode);//随机码
        teacher.setSalt(salt);//盐值
    }
}
