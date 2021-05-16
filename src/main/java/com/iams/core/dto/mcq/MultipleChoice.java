package com.iams.core.dto.mcq;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wei yz
 * @ClassName: MultipleChoice
 * @Description:  选择题题目及选项
 * @date 2021/3/26 22:56
 */
public class MultipleChoice implements Serializable {

    /**
     * 序号 如：1
     */
    private Integer id;

    /**
     * 题目 如：Java的源代码后缀名是什么？
     */
    private String title;

    /**
     * 选项集合
     */
    private List<Choice> choices;



    public Integer getId() {
        return id;
    }

    public MultipleChoice setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MultipleChoice setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public MultipleChoice setChoices(List<Choice> choices) {
        this.choices = choices;
        return this;
    }

    @Override
    public String toString() {
        return "MultipleChoice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", choices=" + choices +
                '}';
    }
}
