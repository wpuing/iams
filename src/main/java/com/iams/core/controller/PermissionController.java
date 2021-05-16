package com.iams.core.controller;


import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.common.util.Utils;
import com.iams.core.dto.PermissionDto;
import com.iams.core.pojo.Permission;
import com.iams.core.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
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
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @RequestMapping("/list")
    public String find(Model model) {
        List<PermissionDto> permissionDtoList = permissionService.findAll();
        model.addAttribute("permissionDtoList", permissionDtoList);
        return "/superAdmin/role-permission-list";
    }

    @RequestMapping("/all/add.html")
    public String allAdd(Model model) {
        List<Permission> permissionList = permissionService.find(null, "menu");
        model.addAttribute("menuList",permissionList);
        return "/superAdmin/permission-add";
    }

    @RequestMapping("/role/add.html")
    public String roleAdd(Integer roleId,Model model) {
        if(!Utils.isEmpty(roleId)){
            return "404";
        }
        List<Permission> menuList = permissionService.find(null,"menu");
        List<Permission> permissionList = permissionService.findRoleAllMenu(roleId);
        model.addAttribute("menuList",menuList);
        model.addAttribute("permissionList",permissionList);
        model.addAttribute("roleId",roleId);
        return "/superAdmin/role-permission-add";
    }

    @RequestMapping("/update.html")
    public String allUpdate(String type,Integer id,Model model) {
        if(!Utils.isEmpty(type)||!Utils.isEmpty(id)){
            return "404";
        }
        Permission permission = permissionService.find(id);
        if(type.equals("permission")&&permission.getType().equals("permission")){
            model.addAttribute("menuPermission",permissionService.find(permission.getParentId()));
            List<Permission> permissionList = permissionService.find(null, "menu");
            model.addAttribute("menuList",permissionList);
        }
        model.addAttribute("type",type);
        model.addAttribute("permission",permission);
        return "/superAdmin/permission-update";
    }

    @RequestMapping("/findPermission")
    @ResponseBody
    public Result findPermission(Integer parentId) {
        if(Utils.isEmpty(parentId)){
            //根据父id查询该后继权限
            List<Permission> permissionList = permissionService.find(parentId, "permission");
            return ResultGenerator.genSuccessResult(permissionList);
        }
        return ResultGenerator.genSuccessResult(null);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Result add(@Valid Permission permission) {
        if(permissionService.insert(permission)<=0){
            return  ResultGenerator.genFailResult("添加失败！");
        }
        return ResultGenerator.genSuccessResult();
    }


    @RequestMapping("/update")
    @ResponseBody
    public Result update(@Valid Permission permission) {
        if(permissionService.update(permission)<=0){
            return ResultGenerator.genFailResult("修改失败！");
        }
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id") Integer id) {
        if(permissionService.delete(id)<=0){
            return ResultGenerator.genFailResult("删除失败！id:"+id);
        }
        return ResultGenerator.genSuccessResult();
    }

}
