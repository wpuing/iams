package com.iams.core.service.impl;

import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.JudgmentMapper;
import com.iams.core.pojo.Judgment;
import com.iams.core.service.JudgmentService;
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
public class JudgmentServiceImpl implements JudgmentService {

    @Autowired
    private JudgmentMapper judgmentMapper;

    @Override
    public Judgment find(Integer id) {
        return sel(id);
    }

    @Override
    public int update(Judgment judgment) {
        sel(judgment.getId());
        return judgmentMapper.updateById(judgment);
    }

    @Override
    public int insert(Judgment judgment) {
        return judgmentMapper.insert(judgment);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return judgmentMapper.deleteById(id);
    }


    private Judgment sel(Integer id){
        Utils.isEmpty(id,"查询判断题答案失败，答案id为空或小于等于0！");
        Judgment judgment = judgmentMapper.selectById(id);
        if(ObjectUtils.isEmpty(judgment)){
            throw new ParameterException("查询判断题答案失败，id不存在！");
        }
        return judgment;
    }
}
