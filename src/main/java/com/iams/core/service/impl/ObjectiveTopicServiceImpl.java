package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.ObjectiveTopicMapper;
import com.iams.core.pojo.ObjectiveTopic;
import com.iams.core.service.ObjectiveTopicService;
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
public class ObjectiveTopicServiceImpl implements ObjectiveTopicService {

    @Autowired
    private ObjectiveTopicMapper otMapper;

    @Override
    public ObjectiveTopic find(Integer id) {
        return sel(id);
    }

    @Override
    public List<ObjectiveTopic> find(ObjectiveTopic objectiveTopic) {
        LambdaQueryWrapper<ObjectiveTopic> wrapper = Wrappers.<ObjectiveTopic>lambdaQuery();
        if(objectiveTopic!=null){
            if(Utils.isEmpty(objectiveTopic.getTitle())){//题目
                wrapper.like(ObjectiveTopic::getTitle,objectiveTopic.getTitle());
            }
            if(Utils.isEmpty(objectiveTopic.getScore())){//分数
                wrapper.eq(ObjectiveTopic::getScore,objectiveTopic.getScore());
            }
            if(Utils.isEmpty(objectiveTopic.getResult())){//答案
                wrapper.like(ObjectiveTopic::getResult,objectiveTopic.getResult());
            }
            if(Utils.isEmpty(objectiveTopic.getRemark())){//备注
                wrapper.like(ObjectiveTopic::getRemark,objectiveTopic.getRemark());
            }
            if(Utils.isEmpty(objectiveTopic.getTurnout())){//人数
                wrapper.eq(ObjectiveTopic::getTurnout,objectiveTopic.getTurnout());
            }
        }
        return otMapper.selectList(wrapper);
    }

    @Override
    public int insert(ObjectiveTopic objectiveTopic) {
        objectiveTopic.setId(null);
        objectiveTopic.setDeleted(IamsConstants.DELETED);
        return otMapper.insert(objectiveTopic);
    }

    @Override
    public int update(ObjectiveTopic objectiveTopic) {
        sel(objectiveTopic.getId());
        return otMapper.updateById(objectiveTopic);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return otMapper.deleteById(id);
    }

    private ObjectiveTopic sel(Integer id){
        Utils.isEmpty(id,"查询客观题失败，id不能为空或者小于等于0");
        ObjectiveTopic objectiveTopic = otMapper.selectById(id);
        if(objectiveTopic==null){
            throw new ParameterException("查询失败，客观题为空！！！，id: "+id);
        }
        return objectiveTopic;
    }
}
