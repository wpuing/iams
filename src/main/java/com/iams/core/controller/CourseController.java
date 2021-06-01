package com.iams.core.controller;


import com.iams.common.util.LayResult;
import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.CourseDto;
import com.iams.core.mapper.CourseMapper;
import com.iams.core.pojo.Course;
import com.iams.core.pojo.GiveLessons;
import com.iams.core.service.BaseService;
import com.iams.core.service.CourseService;
import com.iams.core.service.GiveLessonsService;
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
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private GiveLessonsService giveLessonsService;

    @Autowired
    private CourseMapper courseMapper;


    @RequestMapping("/list/studentPage")
    public String studentCourseList(String studentId, Model model) {
        if (!Utils.isEmpty(studentId)) {
            return "404";
        }
//        IPage<CourseDto> iPage = courseService.findCourseByStudentId(condition, studentId, pageNum, pageSize);
//        model.addAttribute("data",iPage.getRecords());// 查询到的数据往前端传
//        model.addAttribute("search",condition);// 模糊查询条件值
//        model.addAttribute("size",iPage.getTotal());//总的数据条数
        return "/student/course-list";
    }

    @RequestMapping("/list/{role}")
    //@RequiresPermissions("course:list:page")
    public String list(@PathVariable("role") String role) {
        if (Utils.isEmpty(role) && role.equals("admin")) {
            return "/admin/course-list";
        }
        if (Utils.isEmpty(role) && role.equals("teacher")) {
            return "/teacher/course-list";
        }
        return "/404";
    }

    @RequestMapping("/studentList.html/{id}")
    public String studentListPage(@PathVariable("id") Integer id, Model model) {
        CourseDto dto = courseService.find(id);
        if (dto != null) {
            GiveLessons gl = new GiveLessons()
                    .setId(dto.getGiveId())
                    .setCourseId(dto.getCourseNumber())
                    .setTeacherId(dto.getTeacherNumber());
            model.addAttribute("gl", gl);
            return "/admin/course-student-list";
        }
        return "/404";
    }

    @RequestMapping("/add.html")
    public String add() {
        return "/admin/course-add";
    }

    @RequestMapping("/add.html/{id}")
    public String addForTeacher(@PathVariable("id") String teacherId, Model model) {
        if(!Utils.isEmpty(teacherId)){
            return "404";
        }
        model.addAttribute("teacherId", teacherId);
        return "/teacher/course-add";
    }

    @RequestMapping("/student/add.html")
    public String addForStudent(String studentId, Model model) {
        if(!Utils.isEmpty(studentId)){
            return "404";
        }
        model.addAttribute("studentId", studentId);
        return "/student/student-course-add";
    }

    @RequestMapping("/update.html/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        CourseDto courseDto = courseService.find(id);
        model.addAttribute("course", courseDto);
        return "/admin/course-update";
    }

    @RequestMapping("/addStudent.html/{id}")
    public String addStudent(@PathVariable("id") Integer id, Model model) {
        CourseDto courseDto = courseService.find(id);
        model.addAttribute("course", courseDto);
        return "/admin/course-add-student";
    }

    @ResponseBody
    @RequestMapping("/studentList/{id}")
    public Result studentList(String condition, @PathVariable("id") String courseId, Integer pageNum, Integer pageSize) {
        LayResult layResult = courseService.find(condition, courseId, pageNum, pageSize);
        return ResultGenerator.genSuccessResult(layResult);
    }


    @ResponseBody
    @RequestMapping("/studentCourseList")
    public Result studentCourseList(String condition, String studentId, Integer pageNum, Integer pageSize) {
        LayResult layResult = courseService.findCourseByStudentId(condition, studentId, pageNum, pageSize);
        return ResultGenerator.genSuccessResult(layResult);
    }

    @RequestMapping("/updateTh/{id}")
    public String updateTh(@PathVariable("id") Integer id, Model model) {
        CourseDto courseDto = courseService.find(id);
        model.addAttribute("course", courseDto);
        return "/admin/course-update-teacher";
    }


    @ResponseBody
    @RequestMapping("/find/{id}")
    public Result find(@PathVariable("id") Integer id) {
        CourseDto courseDto = courseService.find(id);
        return ResultGenerator.genSuccessResult(courseDto);
    }

    @ResponseBody
    @RequestMapping("/findAll")
    public Result find(String condition, String teacherId, Integer pageNum, Integer pageSize) {
        if (Utils.isEmpty(teacherId)) {//不为空时为教师查询
            LayResult courseList = courseService.findTeacherCourseList(teacherId, pageNum, pageSize);
            return ResultGenerator.genSuccessResult(courseList);
        }
        LayResult courseList = courseService.find(condition, pageNum, pageSize);
        return ResultGenerator.genSuccessResult(courseList);
    }

    @ResponseBody
    @RequestMapping("/add")
    public Result add(@Valid Course course) {
        return addCourse(course);
    }

    @ResponseBody
    @RequestMapping("/add/teacher/{teacherId}")
    public Result add(@Valid Course course, @PathVariable("teacherId") String teacherId) {
        Utils.isEmpty(teacherId, "教师编号为空，请检查是否已登录！");
        if (add(course).getCode() == 200) {//添加成功则添加课程教师
            GiveLessons g = new GiveLessons()
                    .setCourseId(course.getNumber())
                    .setTeacherId(teacherId);
            giveLessonsService.insert(g, true);
        }
        return ResultGenerator.genSuccessResult();
    }


    @ResponseBody
    @RequestMapping("/update")
    public Result update(@Valid Course course) {
        CourseDto dto = courseService.find(course.getId());
        if (courseService.update(course) <= 0) {
            return ResultGenerator.genFailResult("修改失败！");
        }
        if (!course.getNumber().equals(dto.getCourseNumber())) {//如果编号已被修改，则修改课程的编号
            giveLessonsService.updateNumber(dto.getCourseNumber(), course.getNumber(), 1);
        }
        return ResultGenerator.genSuccessResult();
    }

    @ResponseBody
    @RequestMapping("/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        if (courseService.delete(id) <= 0) {
            return ResultGenerator.genFailResult("删除失败！id:" + id);
        }
        giveLessonsService.delete(courseService.find(id).getCourseNumber());//删除关系
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/deleteByIds/{ids}")
    @ResponseBody
    public Result delete(@PathVariable("ids") String ids) {
        if(BaseService.deleteByIds(ids,courseMapper)<=0){
            return ResultGenerator.genFailResult("删除失败！"+ids);
        }
        return ResultGenerator.genSuccessResult();
    }

    private Result addCourse(Course course) {
        if (courseService.insert(course) <= 0) {
            return ResultGenerator.genFailResult("添加失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

}
