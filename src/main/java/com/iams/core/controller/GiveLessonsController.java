package com.iams.core.controller;


import com.iams.common.constant.IamsConstants;
import com.iams.common.exception.OperationException;
import com.iams.common.exception.ParameterException;
import com.iams.common.util.*;
import com.iams.core.dto.GiveLessonsDto;
import com.iams.core.mapper.GiveLessonsMapper;
import com.iams.core.pojo.GiveLessons;
import com.iams.core.service.BaseService;
import com.iams.core.service.GiveLessonsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Controller
@RequestMapping("/giveLessons")
public class GiveLessonsController {

    @Autowired
    private GiveLessonsService giveLessonsService;

    @Autowired
    private GiveLessonsMapper giveLessonsMapper;

    /**
     * 上传模板解析
     * @param model
     * @return
     */
    @RequestMapping("/studentNumbers")
    @RequiresPermissions("giveLessons:studentNumbers:page")
    public String imt(String fileName,String giveId,Model model) throws IOException {
        Utils.isEmpty(fileName,"解析失败，文件名为空！！！！！");
        System.out.println("文件："+fileName+"  ,giveId: "+giveId);
        if(!Utils.isEmpty(giveId) || giveId.equals("null")){
            throw new OperationException("该课程没有分配教师！设置归属再进行操作！");
        }
        GiveLessons giveLessons = giveLessonsService.find(Integer.parseInt(giveId));//查询教师课程关系
        if(ObjectUtils.isEmpty(giveLessons)){
            throw new OperationException("该课程没有分配教师！设置归属再进行操作！");
        }
        List<GiveLessons> list = ExcelUtils.importExcel(IamsConstants.NUMBER_FILE_PATH+fileName,0,1, GiveLessons.class);
        List<String> numbers = new ArrayList<>();
        if (list != null && list.size() > 0) {
            // 提取出学号
            numbers=list.stream().map(GiveLessons::getStudentId).collect(Collectors.toList());
        }
        model.addAttribute("numbers",numbers);
        model.addAttribute("giveLessons",giveLessons);
        return "student-number-list";
    }

    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file) {
        String flag = FileUtils.upload(file,IamsConstants.NUMBER_FILE_PATH);
        System.out.println("文件名称2："+flag);
        if(Utils.isEmpty(flag)){
            return ResultGenerator.genSuccessResult(flag);
        }
        return ResultGenerator.genFailResult("上传失败");
    }

    /**
     * 下载课程学生学号表模板
     */
    @RequestMapping("/download")
    public void load(HttpServletResponse response){
        FileUtils.downloadFile(response, IamsConstants.TEMPLATE_FILE_PATH,
                IamsConstants.COURSE_STUDENT_NUMBER_TMP,IamsConstants.DOWNLOAD_NAME_TMP1);
    }


    @RequestMapping("/adds")
    @ResponseBody
    public Result adds(@Valid GiveLessonsDto giveLessons) {
        Utils.isEmpty(giveLessons.getNumbers(),"批量添加的学号不能为空！！！！");
        int result = giveLessonsService.insert(giveLessons);
        if(result<=0){
            return ResultGenerator.genFailResult("批量添加失败！添加个数为： "+result);
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/add/{role}")
    @ResponseBody
    public Result add(@PathVariable("role") String role, GiveLessons giveLessons) {
        return insertOrUpdate(role,giveLessons,true);
    }

    @RequestMapping("/student/add")
    @ResponseBody
    public Result studentAdd( GiveLessons giveLessons) {
        if(ObjectUtils.isEmpty(giveLessons.getCourseId())){
            return ResultGenerator.genFailResult("添加失败，课程编号为空！");
        }
        //查询是否存在
        List<GiveLessons> list = giveLessonsService.find(new GiveLessons()
                .setStudentId(null)
                .setCourseId(giveLessons.getCourseId()), true);
        if(CollectionUtils.isEmpty(list)){
            return ResultGenerator.genFailResult("该课程数据为空！或没有教师授课！");
        }
        giveLessons.setTeacherId(list.get(0).getTeacherId());//设置教师号
        return insertOrUpdate("student",giveLessons,true);
    }

    @RequestMapping("/update/{role}")
    @ResponseBody
    public Result update(@PathVariable("role") String role, GiveLessons giveLessons) {
        //如果去掉教师号或者是学号，则执行删除
        if(Utils.isEmpty(role) && role.equals("student")){
            if(!Utils.isEmpty(giveLessons.getTeacherId())){//当教师id为空时
                throw new ParameterException("请给该课程添加教师！！！！！");
            }
            if(!Utils.isEmpty(giveLessons.getStudentId())&&Utils.isEmpty(giveLessons.getTeacherId())){
                //当教师id不为空时，学生id为空，则删除该授课信息
                return del(giveLessons.getId());
            }
        }
        if(Utils.isEmpty(role) && role.equals("teacher")){
            if(!Utils.isEmpty(giveLessons.getTeacherId())&&!Utils.isEmpty(giveLessons.getStudentId())){//教师课程
                //当教师id为空，学生id为空，则为删除该教师课程
                return del(giveLessons.getId());
            }
        }
        //判断关系id是否存在，存在则更新不存在则执行添加
        return insertOrUpdate(role,giveLessons,!Utils.isEmpty(giveLessons.getId()));
    }

    @ResponseBody
    @RequestMapping("/delete/{id}")
    @RequiresPermissions("giveLessons:delete:operation")
    public Result delete(@PathVariable("id") Integer id) {
        return del(id);
    }

    @RequestMapping("/deleteByIds")
    @ResponseBody
    @RequiresPermissions("giveLessons:deleteByIds:operation")
    public Result delete(String ids) {
        if(!Utils.isEmpty(ids)){
            return ResultGenerator.genFailResult("编号为空！");
        }
        if(BaseService.deleteByIds(ids,giveLessonsMapper)<=0){
            return ResultGenerator.genFailResult("删除失败！"+ids);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 抽取删除
     */
    private Result del(Integer id){
        if(giveLessonsService.delete(id)<=0){
            return ResultGenerator.genFailResult("删除失败！id:"+id);
        }
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 抽取添加和更新
     */
    private Result insertOrUpdate(String role, GiveLessons giveLessons,boolean type) {
        boolean flag = true;
        if(Utils.isEmpty(role) && role.equals("student")){
            flag = false;
        }
        if(type){
            if(giveLessonsService.insert(giveLessons,flag)<=0){
                return  ResultGenerator.genFailResult("添加失败！");
            }
        }
        if(!type){
            if(giveLessonsService.update(giveLessons,flag)<=0){
                return  ResultGenerator.genFailResult("修改失败！");
            }
        }
        return ResultGenerator.genSuccessResult();
    }
}
