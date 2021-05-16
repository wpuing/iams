package com.iams.common.util;

/**
 * @author Wei yz
 * @ClassName: LayResult
 * @Description: layui 表格统一格式
 * @date 2021/2/9 19:03
 */
public class LayResult {

    /**
     * 状态 0为正常
     */
    private Integer code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 记录数
     */
    private long count;

    /**
     * 数据
     */
    private Object data;

    public Integer getCode() {
        return code;
    }

    public LayResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public LayResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public long getCount() {
        return count;
    }

    public LayResult setCount(long count) {
        this.count = count;
        return this;
    }

    public Object getData() {
        return data;
    }

    public LayResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "LayResult{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
