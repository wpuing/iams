package com.iams.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iams.core.dto.student.StudentResultDto;
import com.iams.core.dto.scores.StudentScoresDto;
import com.iams.core.pojo.StudentResult;
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
public interface StudentResultMapper extends BaseMapper<StudentResult> {

    List<StudentResultDto> selectTopicByNumber(Map<String, Object> paramsMap);

    List<StudentScoresDto> selectScoresByNumber(Map<String, Object> paramsMap);

}
