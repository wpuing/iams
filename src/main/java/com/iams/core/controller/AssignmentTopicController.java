package com.iams.core.controller;


import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.core.service.AssignmentTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *   作业题目关系 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@RestController
@RequestMapping("/assignmentTopic")
public class AssignmentTopicController {

    @Autowired
    private AssignmentTopicService assignmentTopicService;

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        if(assignmentTopicService.delete(id)<=0){
            return ResultGenerator.genFailResult("删除失败！id:"+id);
        }
        return ResultGenerator.genSuccessResult();
    }
}
