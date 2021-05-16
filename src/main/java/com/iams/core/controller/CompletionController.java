package com.iams.core.controller;


import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.core.pojo.ObjectiveTopic;
import com.iams.core.service.AssignmentTopicService;
import com.iams.core.service.ObjectiveTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *   填空题答案 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/completion")
public class CompletionController {


    @Autowired
    private ObjectiveTopicService otService;//客观题业务

    @Autowired
    private AssignmentTopicService atService;//作业题目关系业务


    @RequestMapping("/add")
    @ResponseBody
    private Result add(ObjectiveTopic objectiveTopic){
        System.out.println("数据："+objectiveTopic);
//        //得到选项和题目
//        MultipleChoice multipleChoice = JSONObject.parseObject(objectiveTopic.getTitle(), MultipleChoice.class);
//        if(otService.insert(objectiveTopic)>0){//添加题目,选择题选项为4的话就是单选题为1反之为多选题2
//            AssignmentTopic at = new AssignmentTopic()
//                    .setAssignmentId(objectiveTopic.getAssignmentId())
//                    .setTypeId(multipleChoice.getChoices().size()==4? IamsConstants.SINGLE_TOPIC:IamsConstants.MULTITERM_TOPIC)
//                    .setTopicId(objectiveTopic.getId());
//            if(atService.insert(at)>0){//添加关系
//                return ResultGenerator.genSuccessResult();
//            }
//        }
        return ResultGenerator.genSuccessResult();
        //return ResultGenerator.genFailResult("添加作业失败！！！");
    }
}
