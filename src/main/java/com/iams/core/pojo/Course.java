package com.iams.core.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 *   课程
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程号
     */
    @NotNull(message = "课程号不能为空！")
    private String number;

    /**
     * 名称
     */
    @NotNull(message = "课程名称不能为空！")
    private String name;

    public Integer getId() {
        return id;
    }

    public Course setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getNumber() {
        return number;
    }

    public Course setNumber(String number) {
        this.number = number;
        return this;
    }
    public String getName() {
        return name;
    }

    public Course setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + id +
            ", number=" + number +
            ", name=" + name +
        "}";
    }
}
