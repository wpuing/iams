package com.iams.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.LayResult;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.MessageDto;
import com.iams.core.dto.MessageUserDto;
import com.iams.core.mapper.MessageMapper;
import com.iams.core.pojo.Message;
import com.iams.core.pojo.Student;
import com.iams.core.pojo.Teacher;
import com.iams.core.service.BaseService;
import com.iams.core.service.MessageService;
import com.iams.core.service.StudentService;
import com.iams.core.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;


    @Override
    public Message find(Integer id) {
        return sel(id);
    }

    @Override
    public LayResult find(String condition, String status, String type,Integer userId,String startTime, String endTime, Integer pageNum, Integer pageSize) {
        BaseService.checkPage(pageNum,pageSize);//检查分页参数
        LambdaQueryWrapper<Message> wrapper = Wrappers.<Message>lambdaQuery();
        Page<Message> page = new Page<>(pageNum,pageSize,true);
        BaseService.dateRange(wrapper,startTime,endTime);//时间
        if(Utils.isEmpty(condition)){
            wrapper.like(Message::getTitle,condition)
                    .or()
                    .like(Message::getContent,condition);
        }
        if(Utils.isEmpty(status)){
            wrapper.eq(Message::getStatus,status);
        }
        if(Utils.isEmpty(type)){
            wrapper.eq(Message::getType,type);
        }
        if(Utils.isEmpty(userId)){
            wrapper.eq(Message::getUserId,userId);
        }
        IPage<Message> iPage = messageMapper.selectPage(page,wrapper);
        List<Message> messageList = iPage.getRecords();
        List<MessageDto> messageDtoList = new ArrayList<>();
        if(messageList!=null &&messageList.size()>0){
            for (Message m :messageList){
                MessageDto messageDto = new MessageDto();
                BeanUtils.copyProperties(m, messageDto);//拷贝
                messageDtoList.add(messageDto);
            }
        }
        return ResultGenerator.getData(messageDtoList,iPage.getTotal());
    }

    @Override
    public MessageUserDto getUserInfo(String role, Integer id) {
        MessageUserDto userDto = new MessageUserDto();
        if(Utils.isEmpty(id)&&role.equals("teacher")){
            Teacher teacher = teacherService.find(id);
            userDto.setUserId(teacher.getId())
                    .setAuthor(teacher.getName());
        }
        if(Utils.isEmpty(id)&&role.equals("student")){
            Student student = studentService.find(id);
            userDto.setUserId(student.getId())
                    .setAuthor(student.getName());
        }
        userDto.setType(role);
        return userDto;
    }

    @Override
    public int insert(Message message) {
        message.setStatus("未解决");
        return messageMapper.insert(message);
    }

    @Override
    public int update(Message message) {
        sel(message.getId());
        return messageMapper.updateById(message);
    }

    @Override
    public int delete(Integer id) {
        sel(id);
        return messageMapper.deleteById(id);
    }

    private Message sel(Integer id){
        Utils.isEmpty(id,"查询的留言id为空或者小于等于0！");
        Message message = messageMapper.selectById(id);
        if(message==null){
            throw new ParameterException("查询留言失败！id不存在！");
        }
        return message;
    }
}
