package com.iams.core.controller;


import com.iams.common.util.LayResult;
import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.MessageReply;
import com.iams.core.mapper.MessageMapper;
import com.iams.core.pojo.Message;
import com.iams.core.service.BaseService;
import com.iams.core.service.MessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageMapper messageMapper;

    @RequestMapping("/add.html/{role}/{id}")
    @RequiresPermissions("message:add:page")
    public String add(@PathVariable("role") String role,@PathVariable("id")Integer id, Model model){
        if(!Utils.isEmpty(role)){//管理员
            return "404";
        }
        model.addAttribute("user",messageService.getUserInfo(role,id));
        return "/message-add";
    }


    @RequestMapping("/list/{role}")
    @RequiresPermissions("message:list:page")
    public String list(@PathVariable("role") String role, Model model){
        if(!Utils.isEmpty(role)){//管理员
            return "404";
        }
        model.addAttribute("role",role);
        return "/message-list";
    }

    @RequestMapping("/find.html/{id}")
    @RequiresPermissions("message:find:page")
    public String find(@PathVariable("id") Integer id, Model model) {
        Message message = messageService.find(id);
        model.addAttribute("message",message);
        return "/message-info";
    }

    @RequestMapping("/update.html/{id}")
    @RequiresPermissions("message:update:page")
    public String update(@PathVariable("id") Integer id, Model model) {
        Message message = messageService.find(id);
        model.addAttribute("message",message);
        return "/message-update";
    }


    @RequestMapping("/find/{id}")
    @ResponseBody
    public Result find(@PathVariable("id") Integer id) {
        Message message = messageService.find(id);
        return ResultGenerator.genSuccessResult(message);
    }

    @RequestMapping("/findAll/{role}/{userId}")
    @ResponseBody
    public Result find(@PathVariable("role") String role,@PathVariable("userId") Integer userId, String condition,
                       String status, String type, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        LayResult messageList=null;
        if(!Utils.isEmpty(userId)){
            ResultGenerator.genFailResult("用户不存在或未登录！");
        }
        if(Utils.isEmpty(role)&&role.equals("admin")){//管理员
            messageList = messageService.find(condition, status, type,null, startTime, endTime, pageNum, pageSize);
        }
        if(Utils.isEmpty(role)&&role.equals("teacher")){//教师
            Utils.isEmpty(userId,"查询留言失败，教师id为空！！！！");
            messageList = messageService.find(condition, status, "teacher",userId, startTime, endTime, pageNum, pageSize);
        }
        if(Utils.isEmpty(role)&&role.equals("student")){//学生
            Utils.isEmpty(userId,"查询留言失败，学生id为空！！！！");
            messageList = messageService.find(condition, status, "student",userId, startTime, endTime, pageNum, pageSize);
        }
        return ResultGenerator.genSuccessResult(messageList);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@Valid Message message) {
        if (messageService.insert(message) <= 0) {
            return ResultGenerator.genFailResult("添加失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(MessageReply reply) {
        Message message = messageService.find(reply.getId());
        message.setContent(message.getContent()+"\n"+"\r\n 回复内容如下：\n"+reply.getReply()+"\t\n回复时间："+Utils.getNowDate());
        message.setStatus(reply.getStatus());
        if (messageService.update(message) <= 0) {
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    @RequiresPermissions("message:delete:operation")
    public Result delete(@PathVariable("id") Integer id) {
        if (messageService.delete(id) <= 0) {
            return ResultGenerator.genFailResult("删除失败！id:" + id);
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/deleteByIds")
    @ResponseBody
    @RequiresPermissions("message:deleteByIds:operation")
    public Result delete(String ids) {
        if(!Utils.isEmpty(ids)){
            return ResultGenerator.genFailResult("编号为空！");
        }
        if(BaseService.deleteByIds(ids,messageMapper)<=0){
            return ResultGenerator.genFailResult("删除失败！"+ids);
        }
        return ResultGenerator.genSuccessResult();
    }

}
