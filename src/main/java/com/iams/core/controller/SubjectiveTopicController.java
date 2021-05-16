package com.iams.core.controller;


import com.iams.common.constant.IamsConstants;
import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.pojo.AssignmentTopic;
import com.iams.core.pojo.SubjectiveTopic;
import com.iams.core.service.AssignmentTopicService;
import com.iams.core.service.SubjectiveTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/subjectiveTopic")
public class SubjectiveTopicController {

    @Autowired
    private SubjectiveTopicService stService;//客观题业务

    @Autowired
    private AssignmentTopicService atService;//作业题目关系业务

    /**
     * 修改题目
     * @param id 题目id
     * @param model
     * @return
     */
    @RequestMapping("/update.html/{id}")
    private String update( @PathVariable("id") Integer id, Model model){
        if(!Utils.isEmpty(id)){
            return "404";
        }
        model.addAttribute("sTopic",stService.find(id));
        return "/teacher/subjectiveTopic-update";
    }

    @RequestMapping("/add")
    @ResponseBody
    private Result add(SubjectiveTopic subjectiveTopic) {
        System.out.println("数据：" + subjectiveTopic);
        if (stService.insert(subjectiveTopic) > 0) {//添加题目
            AssignmentTopic at = new AssignmentTopic()
                    .setAssignmentId(subjectiveTopic.getAssignmentId())
                    .setTypeId(IamsConstants.TOPIC_TYPE[4])
                    .setTopicId(subjectiveTopic.getId());
            if (atService.insert(at) > 0) {//添加关系
                return ResultGenerator.genSuccessResult();
            }
        }
        return ResultGenerator.genFailResult("添加作业失败！！！");
    }

    @RequestMapping("/update")
    @ResponseBody
    private Result update(SubjectiveTopic subjectiveTopic) {
        System.out.println("数据：" + subjectiveTopic);
        if (stService.update(subjectiveTopic) > 0) {//修改题目
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("添加作业失败！！！");
    }

}
