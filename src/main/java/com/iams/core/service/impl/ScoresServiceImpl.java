package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.ScoresMapper;
import com.iams.core.pojo.Scores;
import com.iams.core.service.ScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
public class ScoresServiceImpl implements ScoresService {

    @Autowired
    private ScoresMapper scoresMapper;

    @Override
    public Scores find(Integer id) {
        return sel(id);
    }

    @Override
    public Scores find(String studentId, Integer assignmentId) {
        LambdaQueryWrapper<Scores> wrapper = Wrappers.<Scores>lambdaQuery();
        wrapper.eq(Scores::getAssignmentId,assignmentId)
                .eq(Scores::getStudentId,studentId);
        List<Scores> list = scoresMapper.selectList(wrapper);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    @Override
    public int update(Scores scores) {
        sel(scores.getId());
        return scoresMapper.updateById(scores);
    }

    @Override
    public int updateByNumber(Scores scores) {
        LambdaUpdateWrapper<Scores> wrapper = Wrappers.<Scores>lambdaUpdate();
        wrapper.eq(Scores::getAssignmentId,scores.getAssignmentId())
                .eq(Scores::getStudentId,scores.getStudentId());
        return scoresMapper.update(scores,wrapper);
    }

    @Override
    public int insert(Scores scores) {
        return scoresMapper.insert(scores);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return scoresMapper.deleteById(id);
    }

    /**
     * 查询单个成绩
     * @param id
     * @return
     */
    private Scores sel(Integer id){
        Utils.isEmpty(id,"查询成绩失败，编号为空！");
        Scores scores = scoresMapper.selectById(id);
        if(ObjectUtils.isEmpty(scores)){
           throw new ParameterException("数据为空！编号错误或不存在");
        }
        return scores;
    }
}
