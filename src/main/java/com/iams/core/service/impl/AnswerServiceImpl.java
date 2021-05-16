package com.iams.core.service.impl;

import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.dto.AnswerDto;
import com.iams.core.mapper.AnswerMapper;
import com.iams.core.pojo.Answer;
import com.iams.core.service.AnswerService;
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
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Override
    public Answer find(Integer id) {
        return sel(id);
    }

    @Override
    public List<AnswerDto> findByTopicId(Integer assignmentId) {
        Utils.isEmpty(assignmentId,"查询的答疑信息的作业号为空或者小于等于0");
        return answerMapper.findByTopicId(assignmentId);
    }

    @Override
    public int insert(Answer answer) {
        answer.setId(null);
        answer.setStatus(IamsConstants.DELETED);
        return answerMapper.insert(answer);
    }

    @Override
    public int update(Answer answer) {
        sel(answer.getId());
        return answerMapper.updateById(answer);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return answerMapper.deleteById(id);
    }

    private Answer sel(Integer id){
        Utils.isEmpty(id,"查询的答疑id不能小于等于0或为空！");
        Answer answer = answerMapper.selectById(id);
        if(answer==null){
            throw new ParameterException("查询失败，数据为空！");
        }
        return answer;
    }
}
