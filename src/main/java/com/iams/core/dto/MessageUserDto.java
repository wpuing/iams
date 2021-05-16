package com.iams.core.dto;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: MessageUserDto
 * @Description:
 * @date 2021/3/20 13:05
 */
public class MessageUserDto implements Serializable {

    /**
     * 用户类型 学生student 教师teacher
     */
    private String type;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 留言人
     */
    private String author;

    public String getType() {
        return type;
    }

    public MessageUserDto setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public MessageUserDto setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public MessageUserDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    @Override
    public String toString() {
        return "MessageUserDto{" +
                "type='" + type + '\'' +
                ", userId=" + userId +
                ", author='" + author + '\'' +
                '}';
    }
}
