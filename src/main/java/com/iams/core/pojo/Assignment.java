package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *   作业
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程号
     */
    @NotNull(message = "作业课程号不能为空")
    private String courseId;

    /**
     * 教师号
     */
    @NotNull(message = "作业教师号不能为空")
    private String teacherId;

    /**
     * 标题
     */
    @NotNull(message = "作业标题不能为空")
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)//自动创建
    private Date createTime;

    /**
     * 答题人数
     */
    private Integer turnout;

    /**
     * 文件
     */
    private String file;

    /**
     * 限定时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date limitingTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField(select = false)//false：查询时不显示该字段，但是查询时会带上这个字段
    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public Assignment setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getCourseId() {
        return courseId;
    }

    public Assignment setCourseId(String courseId) {
        this.courseId = courseId;
        return this;
    }
    public String getTeacherId() {
        return teacherId;
    }

    public Assignment setTeacherId(String teacherId) {
        this.teacherId = teacherId;
        return this;
    }
    public String getTitle() {
        return title;
    }

    public Assignment setTitle(String title) {
        this.title = title;
        return this;
    }
    public String getDescription() {
        return description;
    }

    public Assignment setDescription(String description) {
        this.description = description;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public Assignment setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Integer getTurnout() {
        return turnout;
    }

    public Assignment setTurnout(Integer turnout) {
        this.turnout = turnout;
        return this;
    }
    public String getFile() {
        return file;
    }

    public Assignment setFile(String file) {
        this.file = file;
        return this;
    }
    public Date getLimitingTime() {
        return limitingTime;
    }

    public Assignment setLimitingTime(Date limitingTime) {
        this.limitingTime = limitingTime;
        return this;
    }
    public Integer getDeleted() {
        return deleted;
    }

    public Assignment setDeleted(Integer deleted) {
        this.deleted = deleted;
        return this;
    }

    @Override
    public String toString() {
        return "Assignment{" +
            "id=" + id +
            ", courseId=" + courseId +
            ", teacherId=" + teacherId +
            ", title=" + title +
            ", description=" + description +
            ", createTime=" + createTime +
            ", turnout=" + turnout +
            ", file=" + file +
            ", limitingTime=" + limitingTime +
            ", deleted=" + deleted +
        "}";
    }
}
