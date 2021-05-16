package com.iams.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iams.core.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
public interface TeacherMapper extends BaseMapper<Teacher> {

    @Select("SELECT * FROM teacher WHERE id = #{id}")
    Teacher selById(@Param("id") Integer id);
}
