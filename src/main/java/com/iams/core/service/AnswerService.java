package com.iams.core.service;

import com.iams.core.dto.AnswerDto;
import com.iams.core.pojo.Answer;

import java.util.List;

/**
 * <p>
 *   答疑 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface AnswerService{

    Answer find(Integer id);

    /**
     * 查询该作业的所有答疑
     * @param assignmentId 作业id
     * @return
     */
    List<AnswerDto> findByTopicId(Integer assignmentId);

    int insert(Answer answer);

    int update(Answer answer);

    int delete(Integer id);

}
