package com.iams.common.util;

import java.util.Map;

/**
 * @author Wei yz
 * @ClassName: LayResult
 * @Description: layui 图片统一格式
 * @date 2021/2/9 19:03
 */
public class LayImageResult {

    /**
     * 状态 0为正常
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 数据
     */
    private Map<String,String> data;

    public Integer getCode() {
        return code;
    }

    public LayImageResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public LayImageResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }


    public Map<String, String> getData() {
        return data;
    }

    public LayImageResult setData(Map<String, String> data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "LayResult{" +
                "code=" + code +
                ", msg='" + msg +
                ", data=" + data +
                '}';
    }
}
