package com.iams.core.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.dto.GiveLessonsDto;
import com.iams.core.mapper.GiveLessonsMapper;
import com.iams.core.pojo.GiveLessons;
import com.iams.core.service.GiveLessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Service
public class GiveLessonsServiceImpl implements GiveLessonsService {

    @Autowired
    private GiveLessonsMapper giveLessonsMapper;

    @Override
    public GiveLessons find(Integer id) {
        return sel(id);
    }

    @Override
    public List<GiveLessons> find(GiveLessons giveLessons, boolean flag) {
        LambdaQueryWrapper<GiveLessons> wrapper = Wrappers.<GiveLessons>lambdaQuery();
        if (Utils.isEmpty(giveLessons.getStudentId())) {
            wrapper.eq(GiveLessons::getStudentId, giveLessons.getStudentId());
        }
        if(!flag){//查询该课程的全部学生学号时选择false
            wrapper.isNotNull(GiveLessons::getStudentId);
        }
        if (Utils.isEmpty(giveLessons.getCourseId())) {
            wrapper.eq(GiveLessons::getCourseId, giveLessons.getCourseId());
        }
        if (Utils.isEmpty(giveLessons.getTeacherId())) {
            wrapper.eq(GiveLessons::getTeacherId, giveLessons.getTeacherId());
        }
        return giveLessonsMapper.selectList(wrapper);
    }

    @Override
    public List<String> find(String teacherId) {
        Utils.isEmpty(teacherId, "check：教师号为空！");
        LambdaQueryWrapper<GiveLessons> wrapper = Wrappers.<GiveLessons>lambdaQuery();
        wrapper.eq(GiveLessons::getTeacherId, teacherId);
        wrapper.isNull(GiveLessons::getStudentId);//学号为空
        List<GiveLessons> giveLessonsList = giveLessonsMapper.selectList(wrapper);
        List<String> numberList = new ArrayList<>();
        if (giveLessonsList != null || giveLessonsList.size() > 0) {
            numberList = giveLessonsList.stream().map(GiveLessons::getCourseId).collect(Collectors.toList());
        }
        return numberList;
    }

    @Override
    public int insert(GiveLessons giveLessons, boolean flag) {
        giveLessons.setId(null);
        check(giveLessons, flag);
        exists(giveLessons, flag);//查询是否冲突
        return giveLessonsMapper.insert(giveLessons);
    }

    @Override
    public int insert(GiveLessonsDto giveLessonsDto) {
        List<String> numbers = JSONObject.parseArray(giveLessonsDto.getNumbers(), String.class);
        if(numbers==null || numbers.size()<=0){
            throw new ParameterException("批量添加失败，学号为空！");
        }
        int count = numbers.size();
        for (String number:numbers) {
            GiveLessons giveLessons = new GiveLessons();
            giveLessons.setId(null)
                    .setTeacherId(giveLessonsDto.getTeacherId())
                    .setCourseId(giveLessonsDto.getCourseId())
                    .setStudentId(number);
            LambdaQueryWrapper<GiveLessons> wrapper = Wrappers.<GiveLessons>lambdaQuery();
            condition(wrapper, giveLessons, false);//查询是否冲突
            if(giveLessonsMapper.selectList(wrapper).size()<=0){//不存在则添加
                giveLessonsMapper.insert(giveLessons);
            }else {//存在相同的就--，不添加
                count--;
            }
        }
        return count;
    }

    @Override
    public int update(GiveLessons giveLessons, boolean flag) {
        sel(giveLessons.getId());//查询检查
        check(giveLessons, flag);
        exists(giveLessons, flag);//查询是否冲突
        return giveLessonsMapper.updateById(giveLessons);
    }

    @Override
    public int delete(Integer id) {
        sel(id);//查询检查
        return giveLessonsMapper.deleteById(id);
    }

    @Override
    public void updateNumber(String beforeNumber, String rearNumber, int type) {
        Utils.isEmpty(beforeNumber,"修改前的编号不能为空");
        Utils.isEmpty(rearNumber,"修改后的编号不能为空");
        LambdaUpdateWrapper<GiveLessons> wrapper = Wrappers.<GiveLessons>lambdaUpdate();
        if(type==0){//课程号
            wrapper.eq(GiveLessons::getCourseId,beforeNumber).set(GiveLessons::getCourseId,rearNumber);
        }
        if(type==1){//学号
            wrapper.eq(GiveLessons::getStudentId,beforeNumber).set(GiveLessons::getStudentId,rearNumber);
        }
        if(type==2){//教师号
            wrapper.eq(GiveLessons::getTeacherId,beforeNumber).set(GiveLessons::getTeacherId,rearNumber);
        }
         giveLessonsMapper.update(null, wrapper);

    }

    @Override
    public void delete(String number) {
        LambdaQueryWrapper<GiveLessons> wrapper = Wrappers.<GiveLessons>lambdaQuery();
        wrapper.eq(GiveLessons::getCourseId,number);
        giveLessonsMapper.delete(wrapper);
    }

    private GiveLessons sel(Integer id) {
        Utils.isEmpty(id, "要查询的信息的id小于等于0或者为空！");
        GiveLessons giveLessons = giveLessonsMapper.selectById(id);
        if ( giveLessons== null) {
            throw new ParameterException("找不到该id: " + id + " 对应的信息！");
        }
        return giveLessons;
    }

    /**
     * 检查数据
     *
     * @param giveLessons 授课信息
     * @param flag
     * @return
     */
    private void check(GiveLessons giveLessons, boolean flag) {
        Utils.isEmpty(giveLessons.getCourseId(), "check：课程号为空！");
        Utils.isEmpty(giveLessons.getTeacherId(), "check：教师号为空！");
        if (flag == false) {
            Utils.isEmpty(giveLessons.getStudentId(), "check：学号为空！");
        }
    }

    /**
     * 查询是否重复添加
     *
     * @param giveLessons 授课信息
     * @param flag
     */
    private void exists(GiveLessons giveLessons, boolean flag) {
        LambdaQueryWrapper<GiveLessons> wrapper = Wrappers.<GiveLessons>lambdaQuery();
        condition(wrapper, giveLessons, flag);
        List<GiveLessons> giveLessonsList = giveLessonsMapper.selectList(wrapper);
        if (giveLessonsList != null && giveLessonsList.size() > 0) {
            throw new ParameterException("重复添加，失败！  ID：" + giveLessonsList.get(0).getId());
        }
    }

    /**
     * 抽出查询条件，减少代码重复
     *
     * @param wrapper
     * @param giveLessons
     * @param flag
     */
    private void condition(LambdaQueryWrapper<GiveLessons> wrapper, GiveLessons giveLessons, boolean flag) {
        if (Utils.isEmpty(giveLessons.getId())) {//id不为空且小于等于0则为修改
            wrapper.ne(GiveLessons::getId, giveLessons.getId());//不等于当前id，排除出来
        }
        wrapper.eq(GiveLessons::getCourseId, giveLessons.getCourseId());//加入课程id
        if (flag == true) {//添加课程教师
            wrapper.isNull(GiveLessons::getStudentId);
        }
        if (flag == false) {//添加授课
            wrapper.eq(GiveLessons::getTeacherId, giveLessons.getTeacherId());
            wrapper.eq(GiveLessons::getStudentId, giveLessons.getStudentId());
        }
    }


}
