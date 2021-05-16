package com.iams.core.dto.tree;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wei yz
 * @ClassName: MenuResult
 * @Description:
 * @date 2021/4/20 22:28
 */
public class MenuResult implements Serializable {

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

    /**
     * 子节点
     */
    private List<Children> children;

    public String getTitle() {
        return title;
    }

    public MenuResult setTitle(String title) {
        this.title = title;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public MenuResult setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public MenuResult setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
        return this;
    }

    public Integer getParentId() {
        return parentId;
    }

    public MenuResult setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getType() {
        return type;
    }

    public MenuResult setType(String type) {
        this.type = type;
        return this;
    }

    public boolean getSpread() {
        return spread;
    }

    public MenuResult setSpread(boolean spread) {
        this.spread = spread;
        return this;
    }

    public List<Children> getChildren() {
        return children;
    }

    public MenuResult setChildren(List<Children> children) {
        this.children = children;
        return this;
    }

    @Override
    public String toString() {
        return "MenuResult{" +
                "title='" + title + '\'' +
                ", id=" + id +
                ", permissionId=" + permissionId +
                ", parentId=" + parentId +
                ", type=" + type +
                ", spread=" + spread +
                ", children=" + children +
                '}';
    }
}
