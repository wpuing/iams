package com.iams.core.service.impl;

import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.SubjectiveTopicMapper;
import com.iams.core.pojo.SubjectiveTopic;
import com.iams.core.service.SubjectiveTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Service
public class SubjectiveTopicServiceImpl implements SubjectiveTopicService {

    @Autowired
    private SubjectiveTopicMapper subjectiveTopicMapper;

    @Override
    public SubjectiveTopic find(Integer id) {
        return sel(id);
    }

    @Override
    public int insert(SubjectiveTopic subjectiveTopic) {
        subjectiveTopic.setId(null);
        subjectiveTopic.setDeleted(IamsConstants.DELETED);
        return subjectiveTopicMapper.insert(subjectiveTopic);
    }

    @Override
    public int update(SubjectiveTopic subjectiveTopic) {
        sel(subjectiveTopic.getId());
        return subjectiveTopicMapper.updateById(subjectiveTopic);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return subjectiveTopicMapper.deleteById(id);
    }

    private SubjectiveTopic sel(Integer id){
        Utils.isEmpty(id,"查询主观题失败，id不能为空或者小于等于0");
        SubjectiveTopic subjectiveTopic = subjectiveTopicMapper.selectById(id);
        if(subjectiveTopic==null){
            throw new ParameterException("查询失败，主观题为空！！！，id: "+id);
        }
        return subjectiveTopic;
    }
}
