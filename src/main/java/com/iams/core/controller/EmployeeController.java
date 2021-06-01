package com.iams.core.controller;


import com.iams.common.util.LayResult;
import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.common.validation.Add;
import com.iams.core.dto.EmployeeDto;
import com.iams.core.mapper.EmployeeMapper;
import com.iams.core.pojo.Employee;
import com.iams.core.service.BaseService;
import com.iams.core.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  员工 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-01
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeMapper employeeMapper;


    @RequestMapping("/list")
    public String list(){
        return "/superAdmin/emp-list";
    }

    @RequestMapping("/add.html")
    public String add(){
        return "/superAdmin/emp-add";
    }

    @RequestMapping("/info")
    public String info(Integer id,String roleName,Model model){
        if(!Utils.isEmpty(roleName)&&!Utils.isEmpty(id)){
            return "404";
        }
        EmployeeDto employeeDto = employeeService.find(id);
        model.addAttribute("employee",employeeDto);
        if(roleName.equals("admin")){
            return "/admin/info";
        }
        return "/superAdmin/info";
    }

    @RequestMapping("/update.html/{id}")
    public String update(@PathVariable("id") Integer id, Model model){
        EmployeeDto emp = employeeService.find(id);
        model.addAttribute("emp", emp);
        return "/superAdmin/emp-update";
    }

    @RequestMapping("/find/{id}")
    @ResponseBody
    public Result find(@PathVariable("id") Integer id) {
        EmployeeDto employeeDto = employeeService.find(id);
        return ResultGenerator.genSuccessResult(employeeDto);
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public Result find(String condition, Integer roleId, String startTime, String endTime, Integer pageNum, Integer pageSize) {
        LayResult employeeVoList = employeeService.find(condition, roleId, startTime, endTime, pageNum, pageSize);
        return ResultGenerator.genSuccessResult(employeeVoList);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@Validated({Add.class}) Employee employee) {
        if(employeeService.insert(employee)<=0){
            return ResultGenerator.genFailResult("添加失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Validated Employee employee) {
        if(employeeService.update(employee,true)<=0){
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateEmail")
    @ResponseBody
    public Result updateEmail(Integer id,String email) {
        Utils.isEmpty(email,"修改的邮箱不能为空！！！");
        Employee employee = employeeService.findById(id);
        employee.setEmail(email);
        if(employeeService.update(employee,true)<=0){
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updatePassword")
    @ResponseBody
    public Result updatePassword(Integer id,String password) {
        Utils.isEmpty(password,"修改的密码不能为空！！！");
        Employee employee = employeeService.findById(id);
        employee.setPassword(password);
        if(employeeService.update(employee,false)<=0){
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        if(employeeService.delete(id)<=0){
            return ResultGenerator.genFailResult("删除失败！id:"+id);
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/deleteByIds/{ids}")
    @ResponseBody
    public Result delete(@PathVariable("ids") String ids) {
        if(BaseService.deleteByIds(ids,employeeMapper)<=0){
            return ResultGenerator.genFailResult("删除失败！"+ids);
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/findByIds")
    @ResponseBody
    public Result find(String ids,Integer pageNum, Integer pageSize) {
        LayResult result = BaseService.findIds(Utils.getIds(ids), pageNum, pageSize, employeeMapper);
        return ResultGenerator.genSuccessResult(result);
    }

}
