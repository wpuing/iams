package com.iams.core.service;

import com.iams.core.dto.GiveLessonsDto;
import com.iams.core.pojo.GiveLessons;

import java.util.List;

/**
 * <p>
 *   课程学生教师 服务类
 * </p>
 *
 * @author Wei yz
 * @since 2021-02-04
 */
public interface GiveLessonsService{

    GiveLessons find(Integer id);

    /**
     * 查询所有授课信息
     * @param giveLessons 授课信息
     * @param flag true查询教师课程信息，false查询教师授课信息
     * @return
     */
    List<GiveLessons> find(GiveLessons giveLessons,boolean flag);

    /**
     * 查询某教师的所有课程id
     * @param teacherId 教师id
     * @return
     */
    List<String> find(String teacherId);

    /**
     * 添加授课信息
     * @param giveLessons 授课信息
     * @param flag true为教师课程，false为教师课程学生
     * @return
     */
    int insert(GiveLessons giveLessons,boolean flag);

    /**
     * 批量添加课程学生
     * @param giveLessonsDto
     * @return
     */
    int insert(GiveLessonsDto giveLessonsDto);

    /**
     * 添加授课信息
     * @param giveLessons 授课信息
     * @param flag true为教师课程，false为教师课程学生
     * @return
     */
    int update(GiveLessons giveLessons,boolean flag);

    /**
     * 删除授课信息
     * @param id 授课id
     * @return
     */
    int delete(Integer id);

    /**
     * 修改编号
     * @param beforeNumber  修改前的编号
     * @param rearNumber 修改后的编号
     * @param type 类型，0为课程号 1为学号 2为教师号
     */
    void updateNumber(String beforeNumber,String rearNumber,int type);

    /**
     * 根据授课编号删除授课信息
     * @param number 授课id
     * @return
     */
    void delete(String number);

}
