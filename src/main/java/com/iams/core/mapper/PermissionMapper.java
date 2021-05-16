package com.iams.core.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.iams.core.dto.PermissionBo;
import com.iams.core.pojo.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
@Repository
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询菜单及菜单下的所有权限
     * @param wrapper 条件
     * @return
     */
    List<PermissionBo> findRolePermissionInfo(@Param(Constants.WRAPPER) Wrapper<Permission> wrapper);

//    /**
//     * 根据角色id查询该角色所有的权限
//     * @param roleId
//     * @return
//     */
//    List<MenuResult> findRolePermissionByRoleId(Integer roleId);

}
