package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.LayResult;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.scores.StudentScoresDetails;
import com.iams.core.mapper.ScoresMapper;
import com.iams.core.pojo.Scores;
import com.iams.core.service.BaseService;
import com.iams.core.service.ScoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public LayResult find(Integer assignmentId, String studentId, Integer pageNum, Integer pageSize) {
        BaseService.checkPage(pageNum,pageSize);//检查分页参数
        LambdaQueryWrapper<Scores> wrapper = Wrappers.<Scores>lambdaQuery();
        Page<Scores> page = new Page<>(pageNum,pageSize,true);
        if(Utils.isEmpty(assignmentId)){
            wrapper.eq(Scores::getAssignmentId,assignmentId);
        }
        if(Utils.isEmpty(studentId)){
            wrapper.like(Scores::getStudentId,studentId);
        }
        Page<Scores> scoresPage = scoresMapper.selectPage(page, wrapper);
        return ResultGenerator.getData(scoresPage.getRecords(),scoresPage.getTotal());
    }

    @Override
    public List<StudentScoresDetails> selectScoresByNumber(Integer assignmentId, String studentNumber) {
        List<StudentScoresDetails> details = new ArrayList<>();
        Integer index=0;
        for(Integer typeId:IamsConstants.TOPIC_TYPE){
            Map<String,Object> map = new HashMap<>();
            map.put("type",typeId);
            map.put("typeId",typeId);
            map.put("assignmentId",assignmentId);
            map.put("studentNumber",studentNumber);
            List<StudentScoresDetails> detailsList = scoresMapper.selectScoresByNumber(map);
            if(typeId==5){//如果是主观题则重新置零
                index=0;
            }
            for(StudentScoresDetails ssd:detailsList){//循环设置题号
                index++;
                ssd.setIndex(index);
            }
            details.addAll(detailsList);
        }
        return details;
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
