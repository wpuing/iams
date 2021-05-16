package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.LayResult;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.assginment.TopicDto;
import com.iams.core.dto.student.StudentTaskDto;
import com.iams.core.mapper.AssignmentMapper;
import com.iams.core.pojo.Assignment;
import com.iams.core.service.AssignmentService;
import com.iams.core.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Override
    public Assignment find(Integer id) {
        return sel(id);
    }

    @Override
    public List<StudentTaskDto> find(String courseNumber, String studentNumber,boolean flag) {
        QueryWrapper<Assignment> query = Wrappers.<Assignment>query();
        query.eq("a.course_id",courseNumber);
        query.eq("st.student_id",studentNumber);
        if(flag){//待完成
            query.and(a->a.apply("date_format(a.limiting_time,'%Y-%m-%d')>{0}", new Date())
                    .or()
                    .isNull("a.limiting_time"));
        }
        if(!flag){//已完成
            query.and(a->a.apply("date_format(a.limiting_time,'%Y-%m-%d')<={0}", new Date())
                    .or()
                    .isNotNull("a.limiting_time"));
        }
        return assignmentMapper.findStudentAssignment(query);
    }

    @Override
    public LayResult find(String condition, String courseId, String teacherId,
                          String startTime, String endTime, Integer pageNum, Integer pageSize) {
        BaseService.checkPage(pageNum, pageSize);//检查分页参数
        LambdaQueryWrapper<Assignment> wrapper = Wrappers.<Assignment>lambdaQuery();
        Page<Assignment> page = new Page<>(pageNum, pageSize, true);
        //创建日期
        BaseService.dateRange(wrapper, startTime, endTime);
        //课程号和教师号
        wrapper.eq(Assignment::getCourseId,courseId)
                .eq(Assignment::getTeacherId,teacherId);
        //模糊条件
        if (Utils.isEmpty(condition)) {
            wrapper.like(Assignment::getTitle, condition)
                    .or()
                    .like(Assignment::getDescription, condition);
        }
        IPage<Assignment> iPage = assignmentMapper.selectPage(page,wrapper);
        return ResultGenerator.getData(iPage.getRecords(),iPage.getTotal());
    }

    @Override
    public List<TopicDto> find(Integer id, Integer typeId, boolean type) {
        Utils.isEmpty(id,"查询作业题目失败，id为空！！！");
        QueryWrapper<Assignment> wrapper = Wrappers.<Assignment>query();
        List<TopicDto> list = new ArrayList<>();
        if(type){
            wrapper.eq("a.assignment_id",id);
            if(typeId!=null && typeId==2){//查询选择题
                wrapper.le("a.type_id",typeId);
            }else {
                wrapper.ge("a.type_id",3);
                wrapper.lt("a.type_id",5);
            }
            wrapper.orderByAsc("a.type_id");
            list = assignmentMapper.selectAllOTopic(wrapper);
        }
        if(!type){
            wrapper.eq("a.assignment_id",id)
                    .eq("a.type_id",typeId);
            list = assignmentMapper.selectAllSTopic(wrapper);
        }
        return list;
    }

    @Override
    public List<TopicDto> find(Integer id, int type) {
        Utils.isEmpty(id,"查询作业题目失败，id为空！！！");
        List<TopicDto> list = new ArrayList<>();
        QueryWrapper<Assignment> wrapper = Wrappers.<Assignment>query();
        wrapper.eq("a.assignment_id",id);//传入作业号
        wrapper.eq("a.type_id",type);
        if(type<5){//客观题
            list = assignmentMapper.selectAllOTopic(wrapper);
        }
        if(type==5){//主观题
            list = assignmentMapper.selectAllSTopic(wrapper);
        }
        return list;
    }

    @Override
    public Float findScore(Integer assignmentId, Integer typeId, boolean type) {
        Utils.isEmpty(assignmentId,"查询的作业总成绩的作业id为空或小于等于0！");
        Map<String,Object> map = new HashMap<>();
        if(type){
            map.put("type","obj");
            map.put("assignmentId",assignmentId);
            map.put("typeId",typeId);
        }
        if(!type){
            map.put("type","sub");
            map.put("assignmentId",assignmentId);
            map.put("typeId",typeId);
        }
        return assignmentMapper.findScore(map);
    }

    @Override
    public int insert(Assignment assignment) {
        assignment.setId(null);
        assignment.setDeleted(IamsConstants.DELETED);
        check(assignment);
        return assignmentMapper.insert(assignment);
    }

    @Override
    public int update(Assignment assignment) {
        sel(assignment.getId());//*查询该作业是否存在
        check(assignment);//检查
        return assignmentMapper.updateById(assignment);
    }

    @Override
    public int delete(Integer id) {
        sel(id);//*查询该作业是否存在
        return assignmentMapper.deleteById(id);
    }

    private Assignment sel(Integer id) {
        Utils.isEmpty(id, "查询的作业id不能为空！！！");
        Assignment assignment = assignmentMapper.selectById(id);
        if (assignment == null) {
            throw new ParameterException("该id查询不到作业！");
        }
        return assignment;
    }

    /**
     * 检查添加的作业是否重复
     * @param assignment 员工信息
     */
    private void check(Assignment assignment) {
        LambdaQueryWrapper<Assignment> wrapper = Wrappers.<Assignment>lambdaQuery();
        if (Utils.isEmpty(assignment.getId())) {//id不为空且小于等于0则为修改
            wrapper.ne(Assignment::getId, assignment.getId());//不等于当前id，排除出来
        }
        wrapper.gt(Assignment::getId, "0")//用于拼接SQL语句
                .eq(Assignment::getCourseId, assignment.getCourseId())//匹配课程号
                .eq(Assignment::getTeacherId, assignment.getTeacherId());//匹配教师号
        wrapper.and(w -> w.eq(Assignment::getTitle, assignment.getTitle()));
        List<Assignment> list = assignmentMapper.selectList(wrapper);
        if (list != null && list.size() > 0) {//存在则取第一条记录的id
            throw new ParameterException("更新失败，更新的信息中已存在该数据，id：" + list.get(0).getId());
        }
    }
}
