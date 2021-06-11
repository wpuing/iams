package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.SpringUtil;
import com.iams.common.util.Utils;
import com.iams.core.mapper.AssignmentTopicMapper;
import com.iams.core.pojo.AssignmentTopic;
import com.iams.core.service.AssignmentTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Service
public class AssignmentTopicServiceImpl implements AssignmentTopicService {

    @Autowired
    private AssignmentTopicMapper assignmentTopicMapper;

    @Override
    public AssignmentTopic find(Integer id) {
        return sel(id);
    }

    @Override
    public List<AssignmentTopic> find(AssignmentTopic assignmentTopic) {
        LambdaQueryWrapper<AssignmentTopic> wrapper = Wrappers.<AssignmentTopic>lambdaQuery();
        if(assignmentTopic!=null){
            if(Utils.isEmpty(assignmentTopic.getTypeId())){//题型号
                wrapper.eq(AssignmentTopic::getTypeId,assignmentTopic.getTypeId());
            }
            if(Utils.isEmpty(assignmentTopic.getAssignmentId())){//作业号
                wrapper.eq(AssignmentTopic::getAssignmentId,assignmentTopic.getAssignmentId());
            }
            if(Utils.isEmpty(assignmentTopic.getTopicId())){//题目号
                wrapper.eq(AssignmentTopic::getTopicId,assignmentTopic.getTopicId());
            }
        }
        return assignmentTopicMapper.selectList(wrapper);
    }

    @Override
    public int insert(AssignmentTopic assignmentTopic) {
        if(assignmentTopicMapper==null){
            assignmentTopicMapper = (AssignmentTopicMapper) SpringUtil.getBean("assignmentTopicMapper");
        }
        return assignmentTopicMapper.insert(assignmentTopic);
    }

    @Override
    public int update(AssignmentTopic assignmentTopic) {
        sel(assignmentTopic.getId());
        return assignmentTopicMapper.updateById(assignmentTopic);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return assignmentTopicMapper.deleteById(id);
    }

    private AssignmentTopic sel(Integer id){
        Utils.isEmpty(id,"查询作业题目关系失败，id为空或小于等于0");
        AssignmentTopic assignmentTopic = assignmentTopicMapper.selectById(id);
        if(assignmentTopic==null){
            throw new ParameterException("作业题目关系为空！查询失败");
        }
        return assignmentTopic;
    }
}
