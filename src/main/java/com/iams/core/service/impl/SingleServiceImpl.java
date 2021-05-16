package com.iams.core.service.impl;

import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.SingleMapper;
import com.iams.core.pojo.Single;
import com.iams.core.service.SingleService;
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
public class SingleServiceImpl implements SingleService {

    @Autowired
    private SingleMapper singleMapper;

    @Override
    public Single find(Integer id) {
        return sel(id);
    }

    @Override
    public int update(Single single) {
        sel(single.getId());
        return singleMapper.updateById(single);
    }

    @Override
    public int insert(Single single) {
        return singleMapper.insert(single);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return singleMapper.deleteById(id);
    }

    private Single sel(Integer id){
        Utils.isEmpty(id,"查询单选题答案失败，答案id为空或小于等于0！");
        Single single = singleMapper.selectById(id);
        if(ObjectUtils.isEmpty(single)){
            throw new ParameterException("查询单选题答案失败，id不存在！");
        }
        return single;
    }
}
