package com.iams.core.service;

import com.iams.core.pojo.ObjectiveTopic;

import java.util.List;

/**
 * <p>
 *   客观题题目 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface ObjectiveTopicService{

    ObjectiveTopic find(Integer id);

    List<ObjectiveTopic> find(ObjectiveTopic objectiveTopic);

    int insert(ObjectiveTopic objectiveTopic);

    int update(ObjectiveTopic objectiveTopic);

    int delete(Integer id);

}
