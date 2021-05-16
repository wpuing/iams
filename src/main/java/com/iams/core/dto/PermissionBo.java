package com.iams.core.dto;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: PermissionBo
 * @Description: 权限组合类
 * @date 2021/4/22 22:25
 */
public class PermissionBo implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 角色关系id
     */
    private Integer rolePermissionId;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型
     */
    private String type;

    /**
     * 资源地址
     */
    private String url;

    /**
     * 权限码
     */
    private String perCode;

    /**
     * 父节点
     */
    private Integer parentId;

    /**
     * 排序
     */
    private Integer sort;

    public Integer getId() {
        return id;
    }

    public PermissionBo setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getRolePermissionId() {
        return rolePermissionId;
    }

    public PermissionBo setRolePermissionId(Integer rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
        return this;
    }

    public String getName() {
        return name;
    }

    public PermissionBo setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public PermissionBo setType(String type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public PermissionBo setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPerCode() {
        return perCode;
    }

    public PermissionBo setPerCode(String perCode) {
        this.perCode = perCode;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public PermissionBo setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public Integer getSort() {
        return sort;
    }

    public PermissionBo setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    @Override
    public String toString() {
        return "PermissionBo{" +
                "id=" + id +
                ", rolePermissionId=" + rolePermissionId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", perCode='" + perCode + '\'' +
                ", parentId=" + parentId +
                ", sort=" + sort +
                '}';
    }
}
