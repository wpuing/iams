package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *   作业题目关系
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class AssignmentTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 题型号
     */
    @NotNull(message = "关系中题型号不能为空")
    private Integer typeId;

    /**
     * 作业号
     */
    @NotNull(message = "关系中作业号不能为空")
    private Integer assignmentId;

    /**
     * 题目号
     */
    @NotNull(message = "关系中题目号不能为空")
    private Integer topicId;

    public Integer getId() {
        return id;
    }

    public AssignmentTopic setId(Integer id) {
        this.id = id;
        return this;
    }
    public Integer getTypeId() {
        return typeId;
    }

    public AssignmentTopic setTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }
    public Integer getAssignmentId() {
        return assignmentId;
    }

    public AssignmentTopic setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
        return this;
    }
    public Integer getTopicId() {
        return topicId;
    }

    public AssignmentTopic setTopicId(Integer topicId) {
        this.topicId = topicId;
        return this;
    }

    @Override
    public String toString() {
        return "AssignmentTopic{" +
            "id=" + id +
            ", typeId=" + typeId +
            ", assignmentId=" + assignmentId +
            ", topicId=" + topicId +
        "}";
    }
}
