package com.iams.core.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iams.core.dto.CourseDto;
import com.iams.core.pojo.Course;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 查询课程教师信息
     * @param page
     * @param wrapper
     * @return
     */
    IPage<CourseDto> selectCourseDtoPage(Page<Course> page , @Param(Constants.WRAPPER) Wrapper<Course> wrapper);


    /**
     * 查询课程教师信息
     * @param page
     * @param wrapper
     * @return
     */
    IPage<CourseDto> selectCourseDtoPageByStudentId(String studentId,Page<Course> page , @Param(Constants.WRAPPER) Wrapper<Course> wrapper);

    /**
     * 查询单课程学生
     * @param page
     * @param wrapper
     * @return
     */
    IPage<CourseDto> selCourseAndStuPage(Page<Course> page , @Param(Constants.WRAPPER) Wrapper<Course> wrapper);

    /**
     * 查询课程dto
     * @param wrapper
     * @return
     */
    CourseDto findDto(@Param(Constants.WRAPPER) Wrapper<Course> wrapper);


}
