package com.iams.core.controller;


import com.alibaba.fastjson.JSON;
import com.iams.common.constant.IamsConstants;
import com.iams.common.util.*;
import com.iams.core.dto.ResultData;
import com.iams.core.dto.result.StudentScantron;
import com.iams.core.dto.result.TopicAnswer;
import com.iams.core.dto.scores.AssignmentScoresDetails;
import com.iams.core.dto.student.AssignmentDetails;
import com.iams.core.dto.student.AssignmentStudentDetails;
import com.iams.core.dto.student.StudentTaskDto;
import com.iams.core.pojo.*;
import com.iams.core.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 作业 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AssignmentTopicService assignmentTopicService;

    @Autowired
    private StudentTaskService studentTaskService;

    @Autowired
    private GiveLessonsService giveLessonsService;

    /**
     * 计算成绩
     */
    @Autowired
    private CorrectService correctService;

    /**
     * 成绩操作
     */
    @Autowired
    private ScoresService scoresService;

    /**
     * 学生作业答案和题目信息业务
     */
    @Autowired
    private UpdateResultService updateResultService;

    @RequestMapping("/list/student")
    public String studentList(String courseNumber, String studentNumber, Model model) {
        Course course = courseService.find(courseNumber);
        if (!Utils.isEmpty(courseNumber) || !Utils.isEmpty(studentNumber) || course == null) {
            return "404";
        }
        List<StudentTaskDto> unfinishedList = assignmentService.find(courseNumber, studentNumber, true);//待完成
        List<StudentTaskDto> finishList = assignmentService.find(courseNumber, studentNumber, false);//完成
        model.addAttribute("course", course);
        model.addAttribute("unfinishedList", unfinishedList);
        model.addAttribute("finishList", finishList);
        return "/student/course-assignment-list";
    }

    @RequestMapping("/list")
    public String list(String courseNumber, String teacherId, Model model) {
        if (!Utils.isEmpty(courseNumber) || !Utils.isEmpty(teacherId)) {
            return "404";
        }
        model.addAttribute("courseId", courseNumber);
        model.addAttribute("teacherId", teacherId);
        return "/teacher/assignment-list";
    }

    @RequestMapping("/findById.html/{id}")
    public String findById(@PathVariable("id") Integer id, Model model) {
        if (!Utils.isEmpty(id)) {
            return "404";
        }
        Float objScore = assignmentService.findScore(id, IamsConstants.TOPIC_TYPE[4], true);//客观题分数
        Float subScore = assignmentService.findScore(id, IamsConstants.TOPIC_TYPE[4], false);//主观题分数
        Double score = (objScore == null ? 0.0 : objScore) + (subScore == null ? 0.0 : subScore);
//        AssignmentTeacherDto atd = findAssignmentDetail(id);
//        model.addAttribute("at",atd);
        AssignmentDetails assignmentDetails = assignmentDetails(id);
        model.addAttribute("ads", assignmentDetails);
        model.addAttribute("score", score);
        return "/teacher/assignment-info";
    }

    /**
     * 学生答题
     * @param id
     * @param studentNumber
     * @param model
     * @return
     */
    @RequestMapping("/student/info")
    public String studentAssignmentInfo1(Integer id, String studentNumber, Model model) {
        if (!Utils.isEmpty(id) || !Utils.isEmpty(studentNumber)) {
            return "404";
        }
        AssignmentStudentDetails details = updateResultService.find(id, studentNumber);
        model.addAttribute("ads", details);
        return "student/student-assignment-info";
    }

    /**
     * 查看成绩
     * @param id
     * @param studentNumber
     * @param model
     * @return
     */
    @RequestMapping("/student/scores")
    public String studentAssignmentScores(Integer id, String studentNumber, Model model) {
        if (!Utils.isEmpty(id) || !Utils.isEmpty(studentNumber)) {
            return "404";
        }
        Float objScore = assignmentService.findScore(id, IamsConstants.TOPIC_TYPE[4], true);//客观题分数
        Float subScore = assignmentService.findScore(id, IamsConstants.TOPIC_TYPE[4], false);//主观题分数
        Double score = (objScore == null ? 0.0 : objScore) + (subScore == null ? 0.0 : subScore);
        AssignmentScoresDetails details = updateResultService.findScores(id, studentNumber);
        model.addAttribute("ads", details);
        model.addAttribute("studentScores", scoresService.find(studentNumber,id).getScore());
        model.addAttribute("teacherScores", score);
        return "student/student-assignment-scores-info";
    }

    @RequestMapping("/add.html/{courseId}/{teacherId}")
    public String add(@PathVariable("courseId") String courseId, @PathVariable("teacherId") String teacherId, Model model) {
        if (!Utils.isEmpty(courseId) || !Utils.isEmpty(teacherId)) {
            return "404";
        }
        model.addAttribute("courseId", courseId);
        model.addAttribute("teacherId", teacherId);
        return "/teacher/assignment-add";
    }

    /**
     * 添加题目
     *
     * @param type  题目类型
     * @param id    作业id
     * @param model
     * @return
     */
    @RequestMapping("/addTopic.html/{type}/{id}")
    private String add(@PathVariable("type") String type, @PathVariable("id") Integer id, Model model) {
        if (!Utils.isEmpty(id)) {
            return "404";
        }
        model.addAttribute("assignmentId", id);
        if (Utils.isEmpty(type) && type.equals("addSingle")) {//添加选择题
            return "/teacher/multipleChoice-add";
        }
        if (Utils.isEmpty(type) && type.equals("addJudgment")) {//添加判断题
            return "/teacher/judgment-add";
        }
        if (Utils.isEmpty(type) && type.equals("addCompletion")) {//添加填空题
            return "/teacher/completion-add";
        }
        if (Utils.isEmpty(type) && type.equals("addSubjective")) {//添加主观题
            return "/teacher/subjectiveTopic-add";
        }
        return "404";
    }

    /**
     * 下载作业文件
     */
    @RequestMapping("/download/{name}")
    public void load(@PathVariable("name") String name, HttpServletResponse response) {
        String[] strArray = name.split("\\.");//分割
        Utils.isEmpty(name, "作业文件名不能为空！！！");
        FileUtils.downloadFile(response, IamsConstants.ASSIGNMENT_FILE_PATH, name,
                "temp" + "." + strArray[strArray.length - 1]);

    }


    @RequestMapping("/update.html/{id}")
    public String update(@PathVariable("id") Integer id, Model model) {
        if (!Utils.isEmpty(id)) {
            return "404";
        }
        Assignment assignment = assignmentService.find(id);
        model.addAttribute("assignment", assignment);
        return "/teacher/assignment-update";
    }

    @RequestMapping("/updateIssue.html")
    public String updateIssue(Integer id, Model model) {
        if (!Utils.isEmpty(id)) {
            return "404";
        }
        List<StudentTask> studentTaskList = studentTaskService.find(id);
        Integer status = 0;
        if (!CollectionUtils.isEmpty(studentTaskList)) {
            status = studentTaskList.get(0).getIsAnswer();
        }
        model.addAttribute("isAnswer", status);
        model.addAttribute("assignmentId", id);
        return "/teacher/is-answer-update";
    }

    /**
     * 学生提交答案
     *
     * @param studentScantron 答题卡
     * @return
     */
    @RequestMapping("/submitResult")
    @ResponseBody
    public Result submitResult(StudentScantron studentScantron) {
        if (!Utils.isEmpty(studentScantron.getTopicAnswerList())) {
            return ResultGenerator.genFailResult("答题内容为空！");
        }
        if (!Utils.isEmpty(studentScantron.getStudentNumber())||!Utils.isEmpty(studentScantron.getAssignmentId())) {
            return ResultGenerator.genFailResult("参数作业编号或学号为空！！");
        }
        List<TopicAnswer> list = JSON.parseArray(studentScantron.getTopicAnswerList(), TopicAnswer.class);
        list.forEach(System.out::println);
        //查询该作业的总分数
        Float objScore = assignmentService.findScore(studentScantron.getAssignmentId(), IamsConstants.TOPIC_TYPE[4], true);//客观题分数
        Float subScore = assignmentService.findScore(studentScantron.getAssignmentId(), IamsConstants.TOPIC_TYPE[4], false);//主观题分数
        Double tscore = (objScore == null ? 0.0 : objScore) + (subScore == null ? 0.0 : subScore);
        //批改后的作业的得分
        Float sscore = correctService.correct(studentScantron, assignmentDetails(studentScantron.getAssignmentId()));
        //添加成绩
        Scores scores = new Scores();
        scores.setAssignmentId(studentScantron.getAssignmentId())
                .setScore(sscore)
                .setStudentId(studentScantron.getStudentNumber());
        //更新成绩表信息
        if (!CollectionUtils.isEmpty(list) && checkStudentResultId(list)) {//不为空,则已经存在答题卡,且为添加
            scoresService.insert(scores);//添加总成绩到数据库
        }
        if (!CollectionUtils.isEmpty(list) && !checkStudentResultId(list)) {//不为空,则已经存在答题卡，且为修改
            scoresService.updateByNumber(scores);//修改总成绩到数据库
        }
        //修改学生任务参数
        StudentTask st = new StudentTask()
                .setAssignmentId(studentScantron.getAssignmentId())
                .setStudentId(studentScantron.getStudentNumber())
                .setStartQuizTime(new Date())//设置开始答题时间
                .setTotalQuizTime(String.valueOf(studentScantron.getTime()));//设置答题完成时间
        studentTaskService.updateByNumber(st);
        System.out.println("作业总分：" + tscore + " , 学生最终成绩：" + sscore);
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 上传作业文件
     *
     * @param file 文件
     * @return 成功则返回作业文件名称
     * @throws IOException
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(@RequestParam("assignmentFile") MultipartFile file) {
        String flag = FileUtils.upload(file, IamsConstants.ASSIGNMENT_FILE_PATH);
        if (Utils.isEmpty(flag)) {
            return ResultGenerator.genSuccessResult(flag);
        }
        return ResultGenerator.genFailResult("上传失败");
    }

    /**
     * 发布作业页面
     *
     * @param courseId 课程编号
     * @param model
     * @return
     */
    @RequestMapping("/issue.html")
    public String issue(String courseId, String teacherId, Integer id, Model model) {
        if (!Utils.isEmpty(courseId) || !Utils.isEmpty(teacherId) || !Utils.isEmpty(id)) {
            return "404";
        }
        GiveLessons g = new GiveLessons()
                .setTeacherId(teacherId)
                .setCourseId(courseId);
        List<GiveLessons> giveLessonsList = giveLessonsService.find(g, false);//拿到课程关系
        //查询作业学生关系id

        //提取学号
        List<String> studentNumbers = giveLessonsList.stream().map(GiveLessons::getStudentId).collect(Collectors.toList());
        model.addAttribute("numbers", studentNumbers);
        model.addAttribute("assignmentId", id);
        return "/teacher/issue-student-number-list";
    }

    /**
     * 上传图片
     *
     * @param file 文件
     * @return 成功则返回作业文件名称
     */
    @RequestMapping(value = "/uploadImg/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImg(@PathVariable("type") String type, @RequestParam("file") MultipartFile file) {
        String name = null;
        String url = null;
        if (Utils.isEmpty(type) && type.equals("objective")) {//客观题
            name = FileUtils.upload(file, IamsConstants.OBJECTIVE_TOPIC_FILE_PATH);
            url = IamsConstants.OBJECTIVE_TOPIC_PATH + name;
        }
        if (Utils.isEmpty(type) && type.equals("subjective")) {//主观题
            name = FileUtils.upload(file, IamsConstants.SUBJECTIVE_TOPIC_FILE_PATH);
            url = IamsConstants.SUBJECTIVE_TOPIC_PATH + name;
        }
        if (Utils.isEmpty(name)) {
            System.out.println("路径：" + url);
            return ResultGenerator.getImgData(url, name);
        }
        return ResultGenerator.genFailResult("主观题文件上传失败！");
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Result find(String condition, String courseId, String teacherId, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        if (!Utils.isEmpty(courseId) || !Utils.isEmpty(teacherId)) {
            return ResultGenerator.genFailResult("参数缺失，请检查！！！！");
        }
        LayResult layResult = assignmentService.find(condition, courseId, teacherId, startTime, endTime, pageNum, pageSize);
        return ResultGenerator.genSuccessResult(layResult);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@Valid Assignment assignment) {
        if (assignmentService.insert(assignment) <= 0) {
            return ResultGenerator.genFailResult("添加失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 发布作业
     *
     * @param resultData 作业id & 学号集合
     * @return
     */
    @RequestMapping("/addIssue")
    @ResponseBody
    public Result addIssue(ResultData resultData) {
        if (!Utils.isEmpty(resultData.getNumbers()) || !Utils.isEmpty(resultData.getAssignmentId())) {
            return ResultGenerator.genFailResult("学号或作业号为空！添加失败");
        }
        List<String> list = JSON.parseArray(resultData.getNumbers(), String.class);
        int insert = studentTaskService.insert(list, resultData.getAssignmentId());
        if (insert <= 0) {
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult("添加了：" + insert + " ,条记录！");
    }

    /**
     * 修改发布
     *
     * @param isAnswer     0或者1
     * @param assignmentId 作业编号
     * @return
     */
    @RequestMapping("/updateIssue")
    @ResponseBody
    public Result updateIssue(Integer isAnswer, Integer assignmentId) {
        if (studentTaskService.update(isAnswer, assignmentId) <= 0) {
            return ResultGenerator.genFailResult("修改失败！未添加学生进该作业！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid Assignment assignment) {
        if (assignmentService.update(assignment) <= 0) {
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        if (assignmentService.delete(id) <= 0) {
            return ResultGenerator.genFailResult("删除失败！id:" + id);
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/deleteTopic/{id}")
    @ResponseBody
    public Result deleteTopic(@PathVariable("id") Integer id) {
        if (assignmentTopicService.delete(id) <= 0) {
            return ResultGenerator.genFailResult("删除失败！id:" + id);
        }
        return ResultGenerator.genSuccessResult();
    }

    private AssignmentDetails assignmentDetails(Integer id) {
        AssignmentDetails assignmentDetails = new AssignmentDetails();
        //作业信息
        assignmentDetails.setAssignment(assignmentService.find(id));
        //单选题
        assignmentDetails.setSingleChoiceList(IamsUtils.convert(assignmentService.find(id, IamsConstants.TOPIC_TYPE[0])));
        //多选题
        assignmentDetails.setMultipleChoiceList(IamsUtils.convert(assignmentService.find(id, IamsConstants.TOPIC_TYPE[1])));
        //判断题
        assignmentDetails.setJudgeTopicList(assignmentService.find(id, IamsConstants.TOPIC_TYPE[2]));
        //填空题
        assignmentDetails.setCompletionTopicList(assignmentService.find(id, IamsConstants.TOPIC_TYPE[3]));
        //主观题
        assignmentDetails.setSubTopicList(assignmentService.find(id, IamsConstants.TOPIC_TYPE[4]));
        IamsUtils.spliceTitle(assignmentDetails);//拼接题目
        return assignmentDetails;
    }

    /**
     * 检查是否是刚答题还是修改
     *
     * @param list 答题卡集合
     * @return true为添加 false为修改
     */
    private boolean checkStudentResultId(List<TopicAnswer> list) {
        for (TopicAnswer topicAnswer : list) {//遍历是否存在学生答案关系的id，存在则表示为修改答题
            if (Utils.isEmpty(topicAnswer.getStudentResultId())) {//存在关系id，则表示在成绩表已经存在成绩了,为修改
                return false;
            }
        }
        return true;
    }
}
