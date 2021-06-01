package com.iams.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iams.core.pojo.StudentTask;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wei yz
 * @since 2021-04-17
 */
@Repository
public interface StudentTaskMapper extends BaseMapper<StudentTask> {

    /**
     * 查询该作业的所有学生的邮箱
     * @param assignmentId
     * @return
     */
    List<String> selectStudentEmails(Integer assignmentId);

}
