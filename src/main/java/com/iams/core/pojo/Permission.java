package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *   权限
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    @NotNull(message = "权限名称不能为空")
    private String name;

    /**
     * 类型
     */
    @NotNull(message = "权限类型不能为空")
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

    /**
     * 逻辑删除 0可用1不可用
     */
    @TableLogic
    @TableField(select = false)//false：查询时不显示该字段，但是查询时会带上这个字段
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public Permission setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }

    public Permission setName(String name) {
        this.name = name;
        return this;
    }
    public String getType() {
        return type;
    }

    public Permission setType(String type) {
        this.type = type;
        return this;
    }
    public String getUrl() {
        return url;
    }

    public Permission setUrl(String url) {
        this.url = url;
        return this;
    }
    public String getPerCode() {
        return perCode;
    }

    public Permission setPerCode(String perCode) {
        this.perCode = perCode;
        return this;
    }
    public Integer getParentId() {
        return parentId;
    }

    public Permission setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
    public Integer getSort() {
        return sort;
    }

    public Permission setSort(Integer sort) {
        this.sort = sort;
        return this;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public Permission setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        return "Permission{" +
            "id=" + id +
            ", name=" + name +
            ", type=" + type +
            ", url=" + url +
            ", perCode=" + perCode +
            ", parentId=" + parentId +
            ", sort=" + sort +
            ", deleted=" + deleted +
        "}";
    }
}
