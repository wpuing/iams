package com.iams.core.dto;

import com.iams.core.pojo.Answer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Wei yz
 * @ClassName: AnswerDto
 * @Description:
 * @date 2021/4/9 18:05
 */
public class AnswerDto implements Serializable {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 作业号
     */
    private Integer assignmentId;


    /**
     * 作者
     */
    private String author;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 子评论
     */
    private List<Answer> kids;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Answer> getKids() {
        return kids;
    }

    public void setKids(List<Answer> kids) {
        this.kids = kids;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "id=" + id +
                ", assignmentId=" + assignmentId +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", kids=" + kids +
                '}';
    }
}
