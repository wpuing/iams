package com.iams.core.service.impl;

import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.SubjectiveMapper;
import com.iams.core.pojo.Subjective;
import com.iams.core.service.SubjectiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Service
public class SubjectiveServiceImpl implements SubjectiveService {

    @Autowired
    private SubjectiveMapper subjectiveMapper;

    @Override
    public Subjective find(Integer id) {
        return sel(id);
    }

    @Override
    public int update(Subjective subjective) {
        sel(subjective.getId());
        return subjectiveMapper.updateById(subjective);
    }

    @Override
    public int insert(Subjective subjective) {
        return subjectiveMapper.insert(subjective);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return subjectiveMapper.deleteById(id);
    }

    private Subjective sel(Integer id){
        Utils.isEmpty(id,"查询主观题答案失败，答案id为空或小于等于0！");
        Subjective subjective = subjectiveMapper.selectById(id);
        if(ObjectUtils.isEmpty(subjective)){
            throw new ParameterException("查询主观题答案失败，id不存在！");
        }
        return subjective;
    }
}
