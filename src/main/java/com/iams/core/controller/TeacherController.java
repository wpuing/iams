package com.iams.core.controller;


import com.iams.common.util.LayResult;
import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.TeacherDto;
import com.iams.core.mapper.TeacherMapper;
import com.iams.core.pojo.Teacher;
import com.iams.core.service.BaseService;
import com.iams.core.service.GiveLessonsService;
import com.iams.core.service.TeacherService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * <p>
 * 教师 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private GiveLessonsService giveLessonsService;

    @Autowired
    private TeacherMapper teacherMapper;

    @RequestMapping("/list")
    @RequiresPermissions("teacher:list:page")
    public String teacher() {
        return "/admin/teacher-list";
    }

    @RequestMapping("/add.html")
    @RequiresPermissions("teacher:add:page")
    public String teacherAdd() {
        return "/admin/teacher-add";
    }

    @RequestMapping("/info")
    @RequiresPermissions("teacher:info:page")
    public String info(Integer id,Model model){
        if(!Utils.isEmpty(id)){
            return "404";
        }
        Teacher teacher = teacherService.find(id);
        TeacherDto teacherDto = new TeacherDto();
        BeanUtils.copyProperties(teacher, teacherDto);//拷贝
        model.addAttribute("teacher",teacherDto);
        return "/teacher/info";
    }

    @RequestMapping("/update.html/{id}")
    @RequiresPermissions("teacher:update:page")
    public String teacherUpdate(@PathVariable("id") Integer id, Model model) {
        Teacher teacher = teacherService.find(id);
        model.addAttribute("teacher", teacher);
        return "/admin/teacher-update";
    }


    @RequestMapping("/find/{id}")
    @ResponseBody
    public Result find(@PathVariable("id") Integer id) {
        Teacher teacher = teacherService.find(id);
        return ResultGenerator.genSuccessResult(teacher);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Result find(String condition, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        LayResult employeeVoList = teacherService.find(condition, startTime, endTime, pageNum, pageSize);
        return ResultGenerator.genSuccessResult(employeeVoList);
    }


    @RequestMapping("/add")
    @ResponseBody
    public Result add(@Valid Teacher teacher) {
        teacher.setPassword(Utils.isEmpty(teacher.getPassword())?teacher.getPassword():teacher.getNumber());
        if (teacherService.insert(teacher) <= 0) {
            return ResultGenerator.genFailResult("添加失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid Teacher teacher) {
        Teacher result = teacherService.find(teacher.getId());
        if (teacherService.update(teacher,true) <= 0) {
            return ResultGenerator.genFailResult("修改失败！");
        }
        if (!teacher.getNumber().equals(result.getNumber())) {//如果编号已被修改，则修改课程教师表的编号
            giveLessonsService.updateNumber(result.getNumber(), teacher.getNumber(), 1);
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateEmail")
    @ResponseBody
    @RequiresPermissions("teacher:updateEmail:operation")
    public Result updateEmail(Integer id,String email) {
        Utils.isEmpty(email,"修改的邮箱不能为空！！！");
        Teacher teacher = teacherService.findById(id);
        teacher.setEmail(email);
        if(teacherService.update(teacher,true)<=0){
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    @RequiresPermissions("teacher:updatePassword:operation")
    public Result updatePassword(Integer id,String password) {
        Utils.isEmpty(password,"修改的密码不能为空！！！");
        Teacher teacher = teacherService.findById(id);
        teacher.setPassword(password);
        if(teacherService.update(teacher,false)<=0){
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    @RequiresPermissions("teacher:delete:operation")
    public Result delete(@PathVariable("id") Integer id) {
        if (teacherService.delete(id) <= 0) {
            return ResultGenerator.genFailResult("删除失败！id:" + id);
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/deleteByIds")
    @ResponseBody
    @RequiresPermissions("teacher:deleteByIds:operation")
    public Result delete(String ids) {
        if(!Utils.isEmpty(ids)){
            return ResultGenerator.genFailResult("编号为空！");
        }
        if(BaseService.deleteByIds(ids,teacherMapper)<=0){
            return ResultGenerator.genFailResult("删除失败！"+ids);
        }
        return ResultGenerator.genSuccessResult();
    }
}
