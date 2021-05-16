package com.iams.core.controller;


import com.iams.common.util.LayResult;
import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.StudentDto;
import com.iams.core.pojo.Student;
import com.iams.core.service.GiveLessonsService;
import com.iams.core.service.StudentService;
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
 * 学生 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private GiveLessonsService giveLessonsService;

    @RequestMapping("/list")
    @RequiresPermissions("student:list:page")
    public String student() {
        return "/admin/student-list";
    }

    @RequestMapping("/add.html")
    @RequiresPermissions("student:add:page")
    public String studentAdd() {
        return "/admin/student-add";
    }

    @RequestMapping("/info")
    public String info(Integer id,Model model){
        if(!Utils.isEmpty(id)){
            return "404";
        }
        Student student = studentService.find(id);
        StudentDto studentDto = new StudentDto();
        BeanUtils.copyProperties(student, studentDto);//拷贝
        model.addAttribute("student",studentDto);
        return "/student/info";
    }

    @RequestMapping("/update.html/{id}")
    @RequiresPermissions("student:update:page")
    public String studentUpdate(@PathVariable("id") Integer id, Model model) {
        Student student = studentService.find(id);
        model.addAttribute("student", student);
        return "/admin/student-update";
    }

    @RequestMapping("/find/{id}")
    @ResponseBody
    public Result find(@PathVariable("id") Integer id) {
        Student student = studentService.find(id);
        return ResultGenerator.genSuccessResult(student);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Result find(String condition, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        LayResult studentVoList = studentService.find(condition, startTime, endTime, pageNum, pageSize);
        return ResultGenerator.genSuccessResult(studentVoList);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@Valid Student student) {
        student.setPassword(Utils.isEmpty(student.getPassword())?student.getPassword():student.getNumber());
        if (studentService.insert(student) <= 0) {
            return ResultGenerator.genFailResult("添加失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid Student student) {
        Student result = studentService.find(student.getId());
        if (studentService.update(student,true) <= 0) {
            return ResultGenerator.genFailResult("修改失败！");
        }
        if (!student.getNumber().equals(result.getNumber())) {//如果编号已被修改，则修改课程学生表的编号
            giveLessonsService.updateNumber(result.getNumber(), student.getNumber(), 1);
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateEmail")
    @ResponseBody
    public Result updateEmail(Integer id,String email) {
        Utils.isEmpty(email,"修改的邮箱不能为空！！！");
        Student student = studentService.find(id);
        student.setEmail(email);
        if(studentService.update(student,true)<=0){
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    public Result updatePassword(Integer id,String password) {
        Utils.isEmpty(password,"修改的密码不能为空！！！");
        Student student = studentService.find(id);
        student.setPassword(password);
        if(studentService.update(student,false)<=0){
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        if (studentService.delete(id) <= 0) {
            return ResultGenerator.genFailResult("删除失败！id:" + id);
        }
        return ResultGenerator.genSuccessResult();
    }
}
