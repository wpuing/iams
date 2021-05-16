package com.iams.core.service;

import com.iams.core.pojo.SubjectiveTopic;

/**
 * <p>
 *   主观题题目 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface SubjectiveTopicService{

    SubjectiveTopic find(Integer id);

    int insert(SubjectiveTopic subjectiveTopic);

    int update(SubjectiveTopic subjectiveTopic);

    int delete(Integer id);

}
