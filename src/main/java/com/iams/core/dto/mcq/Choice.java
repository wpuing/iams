package com.iams.core.dto.mcq;

import java.io.Serializable;

/**
 * @author Wei yz
 * @ClassName: Choice
 * @Description: 选择题选项
 * @date 2021/3/26 22:53
 */
public class Choice implements Serializable {

    /**
     * 序号 如A,B,C,D ...
     */
    private String number;

    /**
     * 选项名 .java .class ...
     */
    private String name;

    /**
     * 是否是答案
     */
    private Integer isKey;

    public String getNumber() {
        return number;
    }

    public Choice setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public Choice setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getIsKey() {
        return isKey;
    }

    public Choice setIsKey(Integer isKey) {
        this.isKey = isKey;
        return this;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", isKey='" + isKey + '\'' +
                '}';
    }
}
