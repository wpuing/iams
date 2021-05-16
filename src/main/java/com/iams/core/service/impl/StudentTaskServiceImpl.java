package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.Utils;
import com.iams.core.mapper.StudentTaskMapper;
import com.iams.core.pojo.StudentTask;
import com.iams.core.service.StudentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author Wei yz
 * @ClassName: StudentTaskServiceImpl
 * @Description:
 * @date 2021/4/17 21:53
 */
@Service
public class StudentTaskServiceImpl implements StudentTaskService {

    @Autowired
    private StudentTaskMapper studentTaskMapper;

    @Override
    public StudentTask findById(Integer id) {
        return sel(id);
    }

    @Override
    public List<StudentTask> find(Integer assignmentId) {
        LambdaQueryWrapper<StudentTask> wrapper = Wrappers.<StudentTask>lambdaQuery();
        wrapper.eq(StudentTask::getAssignmentId,assignmentId);//匹配作业id
        return studentTaskMapper.selectList(wrapper);
    }

    @Override
    public int insert(StudentTask studentTask) {
        studentTask.setId(null);
        if(check(studentTask)){
            return 0;
        }
        return studentTaskMapper.insert(studentTask);
    }

    @Override
    public int insert(List<String> numbers, Integer assignmentId) {
        Utils.isEmpty(assignmentId,"批量添加作业学生的作业id为空或小于等于0");
        int result=0;
        if(!CollectionUtils.isEmpty(numbers)){
            for (String number:numbers){
                StudentTask studentTask = new StudentTask(null,number,assignmentId,0,null,null);
                if(!check(studentTask)){//不存在相同的则添加
                    result = result+studentTaskMapper.insert(studentTask);
                }
            }
        }
        return result;
    }

    @Override
    public int update(StudentTask studentTask) {
        sel(studentTask.getId());
        return studentTaskMapper.updateById(studentTask);
    }

    @Override
    public int update(Integer isAnswer, Integer assignmentId) {
        Utils.isEmpty(assignmentId,"修改作业发布的作业id为空或小于等于0");
        LambdaUpdateWrapper<StudentTask> wrapper = Wrappers.<StudentTask>lambdaUpdate();
        wrapper.eq(StudentTask::getAssignmentId,assignmentId);//匹配作业id
        wrapper.set(StudentTask::getIsAnswer,isAnswer);//修改字段
        return studentTaskMapper.update(null, wrapper);
    }

    @Override
    public int updateByNumber(StudentTask studentTask) {
        LambdaUpdateWrapper<StudentTask> wrapper = Wrappers.<StudentTask>lambdaUpdate();
        wrapper.eq(StudentTask::getAssignmentId,studentTask.getAssignmentId())
                .eq(StudentTask::getStudentId,studentTask.getStudentId());
        return studentTaskMapper.update(studentTask,wrapper);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return studentTaskMapper.deleteById(id);
    }

    private StudentTask sel(Integer id){
        Utils.isEmpty(id,"查询的作业学生关系id为空或小于等于0");
        StudentTask studentTask = studentTaskMapper.selectById(id);
        if(ObjectUtils.isEmpty(studentTask)){
            throw new ParameterException("查询的作业关系为空！！！！");
        }
        return studentTask;
    }

    /**
     * 检查是否重复
     * @param studentTask
     * @return
     */
    private boolean check(StudentTask studentTask){
        LambdaQueryWrapper<StudentTask> wrapper = Wrappers.<StudentTask>lambdaQuery();
        if (Utils.isEmpty(studentTask.getId())) {//id不为空且小于等于0则为修改
            wrapper.ne(StudentTask::getId, studentTask.getId());//不等于当前id，排除出来
        }
        wrapper.gt(StudentTask::getId, "0");//用于拼接SQL语句
        wrapper.and(
                w -> w.eq(StudentTask::getAssignmentId, studentTask.getAssignmentId())
                        .eq(StudentTask::getStudentId, studentTask.getStudentId()));
        List<StudentTask> list = studentTaskMapper.selectList(wrapper);
        if (list != null && list.size() > 0) {//存在则取第一条记录的id
            return true;
        }
        return false;
    }
}
