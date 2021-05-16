package com.iams.core.dto;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: MessageReply
 * @Description:
 * @date 2021/3/21 18:58
 */
public class MessageReply implements Serializable {

    /**
     * 消息id
     */
    private Integer id;

    /**
     * 回复内容
     */
    private String reply;

    /**
     * 回复内容
     */
    private String status;

    @Override
    public String toString() {
        return "MessageReply{" +
                "id=" + id +
                ", reply='" + reply + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
