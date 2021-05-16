package com.iams.core.service;

import com.iams.core.pojo.AssignmentTopic;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 *   作业题目关系 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface AssignmentTopicService{

    AssignmentTopic find(Integer id);

    List<AssignmentTopic> find(AssignmentTopic assignmentTopic);

    int insert(@Valid AssignmentTopic assignmentTopic);

    int update(AssignmentTopic assignmentTopic);

    int delete(Integer id);

}
