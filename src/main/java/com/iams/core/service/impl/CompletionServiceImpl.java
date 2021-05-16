package com.iams.core.service.impl;

import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.CompletionMapper;
import com.iams.core.pojo.Completion;
import com.iams.core.service.CompletionService;
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
public class CompletionServiceImpl implements CompletionService {

    @Autowired
    private CompletionMapper completionMapper;

    @Override
    public Completion find(Integer id) {
        return sel(id);
    }

    @Override
    public int update(Completion completion) {
        sel(completion.getId());
        return completionMapper.updateById(completion);
    }

    @Override
    public int insert(Completion completion) {
        return completionMapper.insert(completion);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return completionMapper.deleteById(id);
    }

    private Completion sel(Integer id){
        Utils.isEmpty(id,"查询填空题答案失败，答案id为空或小于等于0！");
        Completion completion = completionMapper.selectById(id);
        if(ObjectUtils.isEmpty(completion)){
            throw new ParameterException("查询填空题答案失败，id不存在！");
        }
        return completion;
    }
}
