package com.iams.core.controller;


import com.iams.common.util.Result;
import com.iams.common.util.ResultGenerator;
import com.iams.core.pojo.RoleEnum;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *   角色 前端控制器
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @RequestMapping("/findById")
    public Result find(Integer id){
        return ResultGenerator.genSuccessResult(RoleEnum.getRole(id));
    }

    @RequestMapping("/findAll")
    public Result find(){
        return ResultGenerator.genSuccessResult(RoleEnum.list());
    }

}
