package com.iams.core.controller;


import com.alibaba.fastjson.JSONObject;
import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.IamsUtils;
import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.assginment.ObjectiveTopicDto;
import com.iams.core.dto.mcq.MultipleChoice;
import com.iams.core.pojo.AssignmentTopic;
import com.iams.core.pojo.ObjectiveTopic;
import com.iams.core.service.AssignmentTopicService;
import com.iams.core.service.ObjectiveTopicService;
import com.iams.core.service.impl.AssignmentTopicServiceImpl;
import com.iams.core.service.impl.ObjectiveTopicServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/objectiveTopic")
public class ObjectiveTopicController {

    @Autowired
    private ObjectiveTopicService otService;//客观题业务

    @Autowired
    private AssignmentTopicService atService;//作业题目关系业务

    /**
     * 修改题目
     * @param type 题目类型
     * @param id 题目id
     * @param model
     * @return
     */
    @RequestMapping("/updateTopic.html/{type}/{id}/{relationId}")
    @RequiresPermissions("objectiveTopic:updateTopic:page")
    private String update(@PathVariable("type") String type, @PathVariable("id") Integer id,
                          @PathVariable("relationId") Integer relationId,Model model){
        if(!Utils.isEmpty(id) || !Utils.isEmpty(relationId)){
            return "404";
        }
        ObjectiveTopic objectiveTopic = otService.find(id);
        model.addAttribute("relationId",relationId);
        if(Utils.isEmpty(type)&&type.equals("updateSingle")){//修改选择题
            ObjectiveTopicDto choiceDto = new ObjectiveTopicDto();//创建选择题视图类
            BeanUtils.copyProperties(objectiveTopic,choiceDto);//拷贝数据
            MultipleChoice multipleChoice = JSONObject.parseObject(objectiveTopic.getTitle(), MultipleChoice.class);//解析题目
            choiceDto.setMultipleChoice(multipleChoice);//将题目放到视图类
            model.addAttribute("oTopic",choiceDto);
            return "/teacher/multipleChoice-update";
        }
        if(Utils.isEmpty(type)&&type.equals("updateJudgment")){//修改判断题
            model.addAttribute("oTopic",objectiveTopic);
            return "/teacher/judgment-update";
        }
        if(Utils.isEmpty(type)&&type.equals("updateCompletion")){//修改填空题
            model.addAttribute("oTopic",objectiveTopic);
            return "/teacher/completion-update";
        }
        return "404";
    }

    @RequestMapping("/update/{type}/{relationId}")
    @ResponseBody
    private Result update(@PathVariable("type") String type,@PathVariable("relationId") Integer relationId,
                          ObjectiveTopic objectiveTopic){
        if(Utils.isEmpty(type) && type.equals("updateSingle") && Utils.isEmpty(relationId)){//修改选择题
            MultipleChoice multipleChoice = JSONObject.parseObject(objectiveTopic.getTitle(), MultipleChoice.class);
            List<String> result = IamsUtils.extract(multipleChoice.getChoices());//提取选择题选项
            if(result==null || result.size()<=0 ){//答案错误
                return ResultGenerator.genFailResult("修改选择题失败！！！，答案异常");
            }
            Integer typeId = IamsConstants.TOPIC_TYPE[1];
            if(result.size()==1){//单选题
                typeId = IamsConstants.TOPIC_TYPE[0];
            }
            AssignmentTopic at = new AssignmentTopic()
                    .setAssignmentId(objectiveTopic.getAssignmentId())
                    .setTopicId(objectiveTopic.getId())
                    .setTypeId(typeId)
                    .setId(relationId);
            atService.update(at);//修改类型
            objectiveTopic.setResult(IamsUtils.getString(result));//设置答案

        }
        if(otService.update(objectiveTopic)>0){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("修改作业失败！！！");
    }


    @RequestMapping("/add/{type}")
    @ResponseBody
    private Result add(@PathVariable("type") String type, ObjectiveTopic objectiveTopic){
        if(ObjectUtils.isEmpty(otService))otService=new ObjectiveTopicServiceImpl();
        if(ObjectUtils.isEmpty(atService))atService=new AssignmentTopicServiceImpl();
        Integer typeId = null;
        if(Utils.isEmpty(type)&&type.equals("addSingle")){//添加选择题
            MultipleChoice multipleChoice = JSONObject.parseObject(objectiveTopic.getTitle(), MultipleChoice.class);
            List<String> result = IamsUtils.extract(multipleChoice.getChoices());//提取选择题选项
            if(result==null || result.size()<=0 ){//答案错误
                throw new ParameterException("添加选择题失败！！！，答案异常");
            }
            objectiveTopic.setResult(IamsUtils.getString(result));//设置答案
            typeId=result.size()==1?IamsConstants.TOPIC_TYPE[0]:IamsConstants.TOPIC_TYPE[1];//等于1就是单选题
        }
        if(Utils.isEmpty(type)&&type.equals("addJudgment")){//添加判断题
            typeId=IamsConstants.TOPIC_TYPE[2];
        }
        if(Utils.isEmpty(type)&&type.equals("addCompletion")){//添加填空题
            typeId=IamsConstants.TOPIC_TYPE[3];
        }
        System.out.println("otService："+otService+" ,objectiveTopic: "+objectiveTopic);
        if(otService.insert(objectiveTopic)>0){//添加题目,添加成功则创建作业题目关系对象
            AssignmentTopic at = new AssignmentTopic()
                    .setAssignmentId(objectiveTopic.getAssignmentId())
                    .setTypeId(typeId)
                    .setTopicId(objectiveTopic.getId());
            if(atService.insert(at)>0){//添加关系
                return ResultGenerator.genSuccessResult();
            }
        }
        return ResultGenerator.genFailResult("添加作业失败！！！");
    }

}
