package com.iams.core.controller;


import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.LayResult;
import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.assginment.AssignmentParameters;
import com.iams.core.dto.scores.StudentScoresDetails;
import com.iams.core.dto.scores.StudentScoresDto;
import com.iams.core.pojo.*;
import com.iams.core.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/scores")
public class ScoresController {

    @Autowired
    private ScoresService scoresService;//成绩

    @Autowired
    private StudentResultService studentResultService;//学生答案

    @Autowired
    private SingleService singleService;//单选答案

    @Autowired
    private MultitermService multitermService;//多选答案

    @Autowired
    private JudgmentService judgmentService;//判断答案

    @Autowired
    private CompletionService completionService;//填空答案

    @Autowired
    private SubjectiveService subjectiveService;//主观答案

    @Autowired
    private AssignmentScoresService assignmentScoresService;//作业情况

    @RequestMapping("/list")
    @RequiresPermissions("scores:teacherList:page")
    public String list(Integer id, Model model) {
        if (!Utils.isEmpty(id)) {
            return "404";
        }
        model.addAttribute("assignmentId", id);
        return "teacher/scores-list";
    }

    @RequestMapping("/update.html")
    @RequiresPermissions("scores:update:page")
    public String update(Integer id, Model model) {
        if (!Utils.isEmpty(id)) {
            return "404";
        }
        Scores scores = scoresService.find(id);
        model.addAttribute("scores", scores);
        return "teacher/scores-update";
    }

    /**
     * 修改题目成绩信息
     *
     * @param assignmentId
     * @param model
     * @return
     */
    @RequestMapping("/updateTopicScore.html")
    @RequiresPermissions("scores:updateTopicScore:page")
    public String updateTopic(Integer assignmentId, String studentNumber, Integer topicId, Integer typeId
            , Model model) {
        if (!Utils.isEmpty(assignmentId) || !Utils.isEmpty(studentNumber)
                || !Utils.isEmpty(topicId) || !Utils.isEmpty(typeId)) {
            return "404";
        }
        List<StudentScoresDetails> details = scoresService.selectScoresByNumber(assignmentId, studentNumber);
        if (!CollectionUtils.isEmpty(details)) {
            for (StudentScoresDetails ssd : details) {
                if (ssd.getTypeId() == typeId && ssd.getTopicId() == topicId) {
                    model.addAttribute("details", ssd);
                }
            }
        }
        model.addAttribute("assignmentId", assignmentId);
        model.addAttribute("studentNumber", studentNumber);
        return "teacher/scores-update-topic";
    }

    @RequestMapping("/detail.html")
    @RequiresPermissions("scores:detail:page")
    public String detail(Integer id, String studentNumber, Model model) {
        if (!Utils.isEmpty(id) || !Utils.isEmpty(studentNumber)) {
            return "404";
        }
        List<StudentScoresDetails> details = scoresService.selectScoresByNumber(id, studentNumber);
        model.addAttribute("details", details);
        model.addAttribute("assignmentId", id);
        model.addAttribute("studentNumber", studentNumber);
        return "teacher/student-topic-result-info";
    }

    @RequestMapping("/assignmentDetail.html")
    @RequiresPermissions("scores:assignmentDetail:page")
    public String assignmentDetail(Integer id, Model model) {
        if (!Utils.isEmpty(id)) {
            return "404";
        }
        AssignmentParameters parameters = assignmentScoresService.find(id);
        model.addAttribute("parameters", parameters);
        return "teacher/student-topic-score-info";
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Result find(Integer assignmentId, String studentNumber, Integer pageNum, Integer pageSize) {
        LayResult employeeVoList = scoresService.find(assignmentId, studentNumber, pageNum, pageSize);
        return ResultGenerator.genSuccessResult(employeeVoList);
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(Scores scores) {
        if (scoresService.update(scores) <= 0) {
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateTopicScore")
    @ResponseBody
    public Result updateTopicScore(StudentScoresDetails studentScoresDetails
            ,Integer assignmentId,String studentNumber) {
        if (ObjectUtils.isEmpty(studentScoresDetails)
                || !Utils.isEmpty(studentScoresDetails.getResultId())
                || !Utils.isEmpty(studentScoresDetails.getStudentResultId())
                || !Utils.isEmpty(studentScoresDetails.getTypeId())
                || !Utils.isEmpty(assignmentId)|| !Utils.isEmpty(studentNumber)) {
            throw new ParameterException("参数为空，请检查是否有参数！！！");
        }
        if(Utils.isEmpty(studentScoresDetails.getTeacherScores())){ //教师的分数不为空则执行修改
            updateResult(studentScoresDetails);//1.1修改答案表的分数
            //1.2修改学生答案表的分数
            Integer studentResultId = studentScoresDetails.getStudentResultId();
            Float teacherScores = studentScoresDetails.getTeacherScores();
            studentResultService.update(new StudentResult().setId(studentResultId).setScore(teacherScores));
            Float totalScore = totalScore(assignmentId, studentNumber);//计算总分数
            // 1.3修改成绩表的分数
            scoresService.updateByNumber(new Scores().setStudentId(studentNumber).setScore(totalScore)
                    .setAssignmentId(assignmentId));
        }
        return ResultGenerator.genSuccessResult();
    }

    private void updateResult(StudentScoresDetails studentScoresDetails){
        Integer resultId = studentScoresDetails.getResultId();//答案id
        Float teacherScores = studentScoresDetails.getTeacherScores();//教师分
        if(studentScoresDetails.getTypeId()== IamsConstants.TOPIC_TYPE[0]){//单选题
            singleService.update(new Single().setId(resultId).setScore(teacherScores));
        }
        if(studentScoresDetails.getTypeId()== IamsConstants.TOPIC_TYPE[1]){//多选题
            multitermService.update(new Multiterm().setId(resultId).setScore(teacherScores));
        }
        if(studentScoresDetails.getTypeId()== IamsConstants.TOPIC_TYPE[2]){//判断题
            judgmentService.update(new Judgment().setId(resultId).setScore(teacherScores));
        }
        if(studentScoresDetails.getTypeId()== IamsConstants.TOPIC_TYPE[3]){//填空题
            completionService.update(new Completion().setId(resultId).setTeacherScore(teacherScores));
        }
        if(studentScoresDetails.getTypeId()== IamsConstants.TOPIC_TYPE[4]){//主观题
            subjectiveService.update(new Subjective().setId(resultId).setTeacherScore(teacherScores));
        }

    }

    private Float totalScore(Integer assignmentId,String studentNumber){
        List<StudentScoresDto> scoresDtoList = new ArrayList<>();
        for(Integer typeId:IamsConstants.TOPIC_TYPE){
            List<StudentScoresDto> list = studentResultService.selectScoresByNumber(typeId, assignmentId, studentNumber);
            scoresDtoList.addAll(list);
        }
        Float score  = new Float(0.0);
        for (StudentScoresDto studentScoresDto:scoresDtoList){
            if(Utils.isEmpty(studentScoresDto.getTeacherScores())){//教师分数不为空则加入
                score+=studentScoresDto.getTeacherScores();
            }else {//为空则加系统分数
                score+=studentScoresDto.getSysScores();
            }
        }
        return score;
    }

}
