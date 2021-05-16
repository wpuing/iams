package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *   留言
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @NotNull(message = "标题不能为空！")
    private String title;

    /**
     * 用户类型 学生student 教师teacher
     */
    @NotNull(message = "用户类型不能为空！")
    private String type;

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空！")
    private Integer userId;

    /**
     * 留言人
     */
    @NotNull(message = "留言人姓名不能为空！")
    private String author;

    /**
     * 内容
     */
    @NotNull(message = "内容不能为空！")
    private String content;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)//自动创建
    private Date createTime;

    /**
     * 状态
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public Message setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public Message setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getAuthor() {
        return author;
    }

    public Message setAuthor(String author) {
        this.author = author;
        return this;
    }
    public String getContent() {
        return content;
    }

    public Message setContent(String content) {
        this.content = content;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public Message setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getStatus() {
        return status;
    }

    public Message setStatus(String status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", userId=" + userId +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", status='" + status + '\'' +
                '}';
    }
}
