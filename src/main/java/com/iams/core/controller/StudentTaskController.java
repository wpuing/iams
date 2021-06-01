package com.iams.core.controller;


import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.pojo.Assignment;
import com.iams.core.service.AssignmentService;
import com.iams.core.service.MailService;
import com.iams.core.service.StudentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  学生作业关系 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-05-22
 */
@Controller
@RequestMapping("/studentTask")
public class StudentTaskController {

    @Autowired
    private StudentTaskService studentTaskService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        if(studentTaskService.delete(id)<=0){
            return ResultGenerator.genFailResult("删除失败！id:"+id);
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/sendEmail")
    @ResponseBody
    public Result sendEmail(Integer id) throws InterruptedException {
        if(!Utils.isEmpty(id)){
            return  ResultGenerator.genFailResult("作业编号为空！");
        }
        Assignment assignment = assignmentService.find(id); // 查询作业
        List<String> emails = studentTaskService.findEmails(id);//查询所有邮箱
        if(!CollectionUtils.isEmpty(emails) && !ObjectUtils.isEmpty(assignment)){//不为空
            for(String email:emails){ //执行发送
                String title = email+" ,来自网络作业管理系统的温馨提示：该做作业了";
                String context = "hello "+email+" 用户 ,您的作业：" + assignment.getTitle()+"  已到账！";
                mailService.sendSimpleMail(email, title, context);
                Thread.sleep(2 * 1000); //设置暂停的时间 2 秒
            }
        }
        return ResultGenerator.genSuccessResult("发送成功！发送了 "+emails.size()+" 条记录！");
    }



}
