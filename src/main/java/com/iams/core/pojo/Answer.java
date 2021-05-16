package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *   答疑
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 作业号
     */
    @NotNull(message = "答疑信息作业号不能为空")
    private Integer assignmentId;

    /**
     * 父节点
     */
    private Integer parentId;

    /**
     * 作者
     */
    @NotNull(message = "答疑信息作者不能为空")
    private String author;

    /**
     * 内容
     */
    @NotNull(message = "答疑信息内容不能为空")
    private String content;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)//自动创建
    private Date createTime;

    /**
     * 状态 0未读 1已读
     */
    @TableLogic
    @TableField(select = false)//false：查询时不显示该字段，但是查询时会带上这个字段
    private Integer status;

    public Integer getId() {
        return id;
    }

    public Answer setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getAssignmentId() {
        return assignmentId;
    }

    public Answer setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }
    public Integer getParentId() {
        return parentId;
    }

    public Answer setParentId(Integer parentId) {
        this.parentId = parentId;
        return this;
    }
    public String getAuthor() {
        return author;
    }

    public Answer setAuthor(String author) {
        this.author = author;
        return this;
    }
    public String getContent() {
        return content;
    }

    public Answer setContent(String content) {
        this.content = content;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public Answer setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getStatus() {
        return status;
    }

    public Answer setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + id +
            ", assignmentId=" + assignmentId +
            ", parentId=" + parentId +
            ", author=" + author +
            ", content=" + content +
            ", createTime=" + createTime +
            ", status=" + status +
        "}";
    }
}
