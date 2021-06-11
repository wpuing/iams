package com.iams.core.service;

import com.iams.core.dto.PermissionDto;
import com.iams.core.pojo.Permission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface PermissionService {

    Permission find(Integer id);

    /**
     * 根据类型查询权限信息
     * @param parentId 父id
     * @param type menu则是菜单 permission则是权限
     * @return
     */
    List<Permission> find(Integer parentId,String type);

    /**
     * 查询该角色的所有菜单
     * @param roleId 角色id
     * @return
     */
    List<Permission> findRoleAllMenu(Integer roleId);


    /**
     * 根据类型查询权限信息
     * @return
     */
    List<PermissionDto> findAll();

    /**
     * 根据角色查询所有的权限信息
     * @param roleId 角色id
     * @return
     */
    List<Permission> findRolePermission(Integer roleId);


    int insert(Permission permission);

    int update(Permission permission);

    int delete(Integer id);

    void updatePermission();
}
