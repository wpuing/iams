package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.LayResult;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.CourseDto;
import com.iams.core.mapper.CourseMapper;
import com.iams.core.pojo.Course;
import com.iams.core.service.BaseService;
import com.iams.core.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;


    @Override
    public CourseDto find(Integer id) {
        select(id);
        QueryWrapper<Course> wrapper = Wrappers.<Course>query();
        wrapper.eq("c.id",id);
        wrapper.isNull("g.student_id");
        return courseMapper.findDto(wrapper);
    }

    @Override
    public Course find(String number) {
        LambdaQueryWrapper<Course> wrapper = Wrappers.<Course>lambdaQuery();
        wrapper.eq(Course::getNumber,number);
        List<Course> courseList = courseMapper.selectList(wrapper);
        if(!CollectionUtils.isEmpty(courseList)){
            return courseList.get(0);
        }
        return null;
    }

    @Override
    public LayResult find(List<String> ids, Integer pageNum, Integer pageSize) {
        BaseService.checkPage(pageNum,pageSize);//检查分页参数
        LambdaQueryWrapper<Course> wrapper = Wrappers.<Course>lambdaQuery();
        Page<Course> page = new Page<>(pageNum, pageSize, true);//开启分页
        if(ids==null ||  ids.size()<=0){//课程id为空
            return ResultGenerator.getData(new ArrayList<Course>(),0);
        }
        wrapper.in(Course::getNumber,ids);//将老师的所授课程的id号添加进条件
        IPage<Course> iPage = courseMapper.selectPage(page,wrapper);
        return ResultGenerator.getData(iPage.getRecords(),iPage.getTotal());
    }

    @Override
    public LayResult findTeacherCourseList(String teacherId, Integer pageNum, Integer pageSize) {
        BaseService.checkPage(pageNum,pageSize);//检查分页参数
        Page<Course> page = new Page<>(pageNum,pageSize,true);
        QueryWrapper<Course> wrapper = Wrappers.<Course>query();
        wrapper.isNull("g.student_id")
                .isNotNull("g.course_id ")
                .eq("g.teacher_id",teacherId);
        IPage<CourseDto> iPage = courseMapper.selectCourseDtoPage(page, wrapper);
        return ResultGenerator.getData(iPage.getRecords(),iPage.getTotal());
    }

    @Override
    public LayResult find(String condition, Integer pageNum, Integer pageSize) {
        BaseService.checkPage(pageNum,pageSize);//检查分页参数
        Page<Course> page = new Page<>(pageNum,pageSize,true);
        QueryWrapper<Course> wrapper = Wrappers.<Course>query();
        wrapper.isNull("g.student_id");
        if((Utils.isEmpty(condition))){
            wrapper.and(w->w.like("c.name",condition)
                    .or()
                    .like("c.number",condition));
        }
        IPage<CourseDto> iPage = courseMapper.selectCourseDtoPage(page, wrapper);
        return ResultGenerator.getData(iPage.getRecords(),iPage.getTotal());
    }

    @Override
    public LayResult find(String condition, String courseId, Integer pageNum, Integer pageSize) {
        Utils.isEmpty(courseId,"查询失败，课程编号为空！");
        BaseService.checkPage(pageNum,pageSize);//检查分页参数
        Page<Course> page = new Page<>(pageNum,pageSize,true);
        QueryWrapper<Course> wrapper = Wrappers.<Course>query();
        wrapper.eq("g.course_id",courseId);//课程编号
        wrapper.isNotNull("g.student_id");
        if((Utils.isEmpty(condition))){
            wrapper.and(w->w.like("g.student_id",condition));//模糊学号
        }
        IPage<CourseDto> iPage = courseMapper.selCourseAndStuPage(page, wrapper);
        return ResultGenerator.getData(iPage.getRecords(),iPage.getTotal());
    }

    @Override
    public LayResult findCourseByStudentId(String condition, String studentId, Integer pageNum, Integer pageSize) {
        Utils.isEmpty(studentId,"查询失败，学号为空！");
        BaseService.checkPage(pageNum,pageSize);//检查分页参数
        Page<Course> page = new Page<>(pageNum,pageSize,true);
        QueryWrapper<Course> wrapper = Wrappers.<Course>query();
        if((Utils.isEmpty(condition))){
            wrapper.and(w->w.like("sc.courseName",condition) //模糊课程名
                            .or()
                            .like("sc.teacherNumber",condition)  //模糊教师名
                            .or()
                            .like("sc.courseNumber",condition)); //模糊课程编号
        }
        IPage<CourseDto> iPage = courseMapper.selectCourseDtoPageByStudentId(studentId,page, wrapper);
        return ResultGenerator.getData(iPage.getRecords(),iPage.getTotal());
    }

    @Override
    public int insert(Course course) {
        course.setId(null);
        checkCourseInfo(course);//往数据库查询是否存在相同编号的课程信息
        return courseMapper.insert(course);
    }

    @Override
    public int update(Course course) {
        select(course.getId());
        checkCourseInfo(course);//往数据库查询是否存在相同编号的课程信息
        return courseMapper.updateById(course);
    }

    @Override
    public int delete(Integer id) {
        select(id);//查询是否存在
        return courseMapper.deleteById(id);
    }


    /**
     * 检查课程信息是否存在相同信息
     * @param course 课程信息
     */
    private void checkCourseInfo(Course course) {
        LambdaQueryWrapper<Course> wrapper = Wrappers.<Course>lambdaQuery();
        if (Utils.isEmpty(course.getId())) {//id不为空且小于等于0则为修改
            wrapper.ne(Course::getId, course.getId());//不等于当前id，排除出来
        }
        wrapper.gt(Course::getId, "0");//用于拼接SQL语句
        wrapper.and(w -> w.eq(Course::getNumber, course.getNumber()));//编号是否一样
        List<Course> list = courseMapper.selectList(wrapper);
        if (list != null && list.size() > 0) {//存在则取第一条记录的id
            throw new ParameterException("更新失败，更新的信息中已存在该数据，id：" + list.get(0).getId());
        }
    }

    /**
     * 根据id查询课程信息，不存在则抛出异常
     * @param id 课程id
     * @return 该id的课程信息
     */
    private Course select(Integer id) {
        Utils.isEmpty(id, "查询的课程id不能小于等于0或为空！");
        Course course = courseMapper.selectById(id);
        if (course == null) {
            throw new ParameterException("查询失败，数据为空！");
        }
        return course;

    }

}
