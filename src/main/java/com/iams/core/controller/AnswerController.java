package com.iams.core.controller;


import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.pojo.Answer;
import com.iams.core.service.AnswerService;
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
 *   答疑 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @RequestMapping("/add.html")
    @RequiresPermissions("answer:add:page")
    public String add(String name,Integer assignmentId,Integer parentId,Model model){
        Utils.isEmpty(name,"用户名为空，请检查！");
        Utils.isEmpty(assignmentId,"作业号为空或不规范，请检查！");
        if(!Utils.isEmpty(parentId)){
            parentId=null;
        }
        model.addAttribute("author",name);
        model.addAttribute("assignmentId",assignmentId);
        model.addAttribute("parentId",parentId);
        return "answer-add";
    }

    @RequestMapping("/update.html/{id}")
    @RequiresPermissions("answer:update:page")
    public String update(@PathVariable("id") Integer id, Model model){
        if(!Utils.isEmpty(id)){
            return "404";
        }
        model.addAttribute("answer",answerService.find(id));
        return "answer-update";
    }

    @RequestMapping("/findInfoByTopicId/{id}")
    @RequiresPermissions("answer:findInfoByTopicId:page")
    public String answerInfo(@PathVariable("id") Integer id, Model model){
        if(!Utils.isEmpty(id)){
            return "404";
        }
        model.addAttribute("answerList",answerService.findByTopicId(id));   //查询该作业的所有答疑信息
        model.addAttribute("assignmentId",id);
        return "answer-info";
    }

    @RequestMapping("/findByTopicId/{id}")
    @ResponseBody
    public Result find(@PathVariable("id") Integer id) {
        return ResultGenerator.genSuccessResult(answerService.findByTopicId(id));
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@Valid Answer answer) {
        if(answerService.insert(answer)<=0){
            return ResultGenerator.genFailResult("添加失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid Answer answer) {
        if(answerService.update(answer)<=0){
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    @RequiresPermissions("answer:delete:operation")
    public Result delete(@PathVariable("id") Integer id) {
        if(answerService.delete(id)<=0){
            return ResultGenerator.genFailResult("删除失败！id:"+id);
        }
        return ResultGenerator.genSuccessResult();
    }

}
