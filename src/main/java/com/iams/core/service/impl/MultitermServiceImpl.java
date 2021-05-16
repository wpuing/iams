package com.iams.core.service.impl;

import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.MultitermMapper;
import com.iams.core.pojo.Multiterm;
import com.iams.core.service.MultitermService;
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
public class MultitermServiceImpl implements MultitermService {

    @Autowired
    private MultitermMapper multitermMapper;

    @Override
    public Multiterm find(Integer id) {
        return sel(id);
    }

    @Override
    public int update(Multiterm multiterm) {
        sel(multiterm.getId());
        return multitermMapper.updateById(multiterm);
    }

    @Override
    public int insert(Multiterm multiterm) {
        return multitermMapper.insert(multiterm);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return multitermMapper.deleteById(id);
    }

    private Multiterm sel(Integer id){
        Utils.isEmpty(id,"查询多选题答案失败，答案id为空或小于等于0！");
        Multiterm multiterm = multitermMapper.selectById(id);
        if(ObjectUtils.isEmpty(multiterm)){
            throw new ParameterException("查询多选题答案失败，id不存在！");
        }
        return multiterm;
    }
}
