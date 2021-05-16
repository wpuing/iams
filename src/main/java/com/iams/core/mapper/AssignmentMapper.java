package com.iams.core.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.iams.core.dto.assginment.TopicDto;
import com.iams.core.dto.student.StudentTaskDto;
import com.iams.core.pojo.Assignment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Repository
public interface AssignmentMapper extends BaseMapper<Assignment> {

    List<TopicDto> selectAllOTopic(@Param(Constants.WRAPPER) Wrapper<Assignment> wrapper);

    List<TopicDto> selectAllSTopic(@Param(Constants.WRAPPER) Wrapper<Assignment> wrapper);

    List<StudentTaskDto> findStudentAssignment(@Param(Constants.WRAPPER) Wrapper<Assignment> wrapper);

    Float findScore(Map<String, Object> paramsMap);

}
