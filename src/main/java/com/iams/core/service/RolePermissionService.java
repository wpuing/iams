package com.iams.core.service;

import com.iams.core.pojo.RolePermission;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 角色权限关系 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface RolePermissionService {

    /**
     * 根据角色id查询该角色的所有权限id
     * @param roleId 角色id
     * @return
     */
    List<Integer> findPermissionIdsByRoleId(Integer roleId);

    int insert(@Valid RolePermission rolePermission);

    int update(@Valid RolePermission rolePermission);

    int delete(Integer id);



}
