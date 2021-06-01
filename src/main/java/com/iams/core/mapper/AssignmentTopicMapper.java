package com.iams.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iams.core.dto.assginment.TopicParameters;
import com.iams.core.pojo.AssignmentTopic;
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
public interface AssignmentTopicMapper extends BaseMapper<AssignmentTopic> {

    List<TopicParameters> findTopicDetails(Map<String, Object> paramsMap);

}
