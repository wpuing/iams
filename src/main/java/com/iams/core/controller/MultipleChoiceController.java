package com.iams.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Wei yz
 * @ClassName: MultipleChoiceController
 * @Description: 选择题前端控制器
 * @date 2021/3/29 15:50
 */
@Controller
@RequestMapping("/multipleChoice")
public class MultipleChoiceController {
//
//    @Autowired
//    private ObjectiveTopicService otService;//客观题业务
//
//    @Autowired
//    private AssignmentTopicService atService;//作业题目关系业务
//
//    @RequestMapping("/add.html/{id}")
//    private String add(@PathVariable("id") Integer id, Model model){
//        System.out.println("id: "+id);
//        if(!Utils.isEmpty(id)){
//            return "404";
//        }
//        model.addAttribute("assignmentId",id);
//        return "/teacher/multipleChoice-add";
//    }
//
//    @RequestMapping("/add")
//    @ResponseBody
//    private Result add(ObjectiveTopic objectiveTopic){
//        //得到选项和题目
//        MultipleChoice multipleChoice = JSONObject.parseObject(objectiveTopic.getTitle(), MultipleChoice.class);
//        if(otService.insert(objectiveTopic)>0){//添加题目,选择题选项为4的话就是单选题为1反之为多选题2
//            AssignmentTopic at = new AssignmentTopic()
//                            .setAssignmentId(objectiveTopic.getAssignmentId())
//                            .setTypeId(multipleChoice.getChoices().size()==4?IamsConstants.TOPIC_TYPE[0]:IamsConstants.TOPIC_TYPE[1])
//                            .setTopicId(objectiveTopic.getId());
//            if(atService.insert(at)>0){//添加关系
//                return ResultGenerator.genSuccessResult();
//            }
//        }
//        return ResultGenerator.genFailResult("添加作业失败！！！");
//    }

}
