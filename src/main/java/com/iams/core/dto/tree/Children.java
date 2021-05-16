package com.iams.core.dto.tree;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: Children
 * @Description:
 * @date 2021/4/20 22:28
 */
public class Children implements Serializable {

    /**
     * 节点标题
     */
    private String title;

    /**
     * 任意唯一键
     */
    private Integer id;

    /**
     * 权限id
     */
    private Integer permissionId;

    /**
     * 父id
     */
    private Integer parentId;

    /**
     * 类型
     */
    private String type;

    /**
     * 是否展开
     */
    private boolean spread = true;

    public String getTitle() {
        return title;
    }

    public Children setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Children setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public Children setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public Children setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getType() {
        return type;
    }

    public Children setType(String type) {
        this.type = type;
        return this;
    }

    public boolean getSpread() {
        return spread;
    }

    public Children setSpread(boolean spread) {
        this.spread = spread;
        return this;
    }

    @Override
    public String toString() {
        return "Children{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", permissionId=" + permissionId +
                ", parentId=" + parentId +
                ", type=" + type +
                ", spread=" + spread +
                '}';
    }
}
