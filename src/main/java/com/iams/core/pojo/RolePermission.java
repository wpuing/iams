package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *   角色权限
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class RolePermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色号
     */
    @NotNull(message = "角色权限的角色号不能为空")
    private Integer roleId;

    /**
     * 权限号
     */
    @NotNull(message = "角色权限的权限号不能为空")
    private Integer permissionId;

    public Integer getId() {
        return id;
    }

    public RolePermission setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getRoleId() {
        return roleId;
    }

    public RolePermission setRoleId(Integer roleId) {
        this.roleId = roleId;
        return this;
    }
    public Integer getPermissionId() {
        return permissionId;
    }

    public RolePermission setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    @Override
    public String toString() {
        return "RolePermission{" +
            "id=" + id +
            ", roleId=" + roleId +
            ", permissionId=" + permissionId +
        "}";
    }
}
