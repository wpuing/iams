package com.iams.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iams.core.dto.AnswerDto;
import com.iams.core.pojo.Answer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {

    List<AnswerDto> findByTopicId(Integer assignmentId);

}
