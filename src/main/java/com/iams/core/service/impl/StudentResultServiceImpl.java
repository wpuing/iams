package com.iams.core.service.impl;

import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.dto.student.StudentResultDto;
import com.iams.core.dto.scores.StudentScoresDto;
import com.iams.core.mapper.StudentResultMapper;
import com.iams.core.pojo.StudentResult;
import com.iams.core.service.StudentResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
public class StudentResultServiceImpl implements StudentResultService {

    @Autowired
    private StudentResultMapper studentResultMapper;

    @Override
    public StudentResult find(Integer id) {
        return sel(id);
    }

    @Override
    public List<StudentResultDto> selectTopicByNumber(Integer typeId, Integer assignmentId, String studentNumber) {
        Utils.isEmpty(typeId,"查询的题目类型不能为空！");
        Utils.isEmpty(assignmentId,"查询的题目编号不能为空！");
        Utils.isEmpty(studentNumber,"查询的学号不能为空！");
        Map<String,Object> map = new HashMap<>();
        map.put("type",typeId);
        map.put("typeId",typeId);
        map.put("assignmentId",assignmentId);
        map.put("studentNumber",studentNumber);
        return studentResultMapper.selectTopicByNumber(map);
    }

    @Override
    public List<StudentScoresDto> selectScoresByNumber(Integer typeId, Integer assignmentId, String studentNumber) {
        Map<String,Object> map = new HashMap<>();
        map.put("type",typeId);
        map.put("typeId",typeId);
        map.put("assignmentId",assignmentId);
        map.put("studentNumber",studentNumber);
        return studentResultMapper.selectScoresByNumber(map);
    }

    @Override
    public int update(StudentResult studentResult) {
        sel(studentResult.getId());
        return studentResultMapper.updateById(studentResult);
    }


    @Override
    public int insert(StudentResult studentResult) {
        return studentResultMapper.insert(studentResult);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return studentResultMapper.deleteById(id);
    }

    private StudentResult sel(Integer id){
        Utils.isEmpty(id,"查询的学生答案信息失败，编号为空或小于等于0");
        StudentResult studentResult = studentResultMapper.selectById(id);
        if(ObjectUtils.isEmpty(studentResult)){
            throw new ParameterException("查询失败，数据为空！或编号不存在！");
        }
        return  studentResult;
    }
}
