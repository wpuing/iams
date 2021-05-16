package com.iams.core.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wei yz
 * @ClassName: PermissionDto
 * @Description: 菜单权限组合类
 * @date 2021/4/19 20:11
 */
public class PermissionDto implements Serializable {

    /**
     * 类型
     */
    private String type;

    /**
     * 类型
     */
    private Integer roleId;

    /**
     * 权限集合
     */
    private List<PermissionBo> permissionList;

    public String getType() {
        return type;
    }

    public PermissionDto setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<PermissionBo> getPermissionList() {
        return permissionList;
    }

    public PermissionDto setPermissionList(List<PermissionBo> permissionList) {
        this.permissionList = permissionList;
        return this;
    }

    @Override
    public String toString() {
        return "PermissionDto{" +
                "type='" + type + '\'' +
                ", roleId=" + roleId +
                ", permissionList=" + permissionList +
                '}';
    }
}
