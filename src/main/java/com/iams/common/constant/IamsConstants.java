package com.iams.common.constant;

/**
 * @author Wei yz
 * @ClassName: IamsConstants
 * @Description: 智能作业管理系统常量类
 * @date 2021/2/1 17:18
 */
public final class IamsConstants {

    public static String BASE_PATH="D:\\CodeSpace\\Java\\Test\\file\\iams";

    /**
     * 映射路径
     */
    public static String MAP_PATH="http://localhost:8488/static/";

    /**
     * 主观题映射路径
     */
    public static String SUBJECTIVE_TOPIC_PATH=MAP_PATH+"subjectivetopic/";

    /**
     * 客观题映射路径
     */
    public static String OBJECTIVE_TOPIC_PATH=MAP_PATH+"objectivetopic/";

    /**
     * 作业映射路径
     */
    public static String ASSIGNMENT_PATH=MAP_PATH+"assignment/";

    /**
     * 逻辑删除参数 0 未删除
     */
    public static Integer DELETED = 0;

    /**
     * 默认分页参数   当前页
     */
    public static Integer PAGE_NUM = 1;

    /**
     * 默认分页参数   每页数
     */
    public static Integer PAGE_SIZE = 10;

    /**
     * 上传的表格文件目录
     */
    public static String NUMBER_FILE_PATH = BASE_PATH +"\\student_number_table\\";

    /**
     * 上传的作业目录
     */
    public static String ASSIGNMENT_FILE_PATH = BASE_PATH + "\\assignment\\";

    /**
     * 上传的客观题目录
     */
    public static String OBJECTIVE_TOPIC_FILE_PATH = BASE_PATH + "\\objectivetopic\\";

    /**
     * 上传的主观题目录
     */
    public static String SUBJECTIVE_TOPIC_FILE_PATH = BASE_PATH + "\\subjectivetopic\\";

    /**
     * 模板目录
     */
    public static String TEMPLATE_FILE_PATH = BASE_PATH +"\\template\\";

    /**
     * 文件目录
     */
    public static String COURSE_STUDENT_NUMBER_TMP = "课程学生学号表.xlsx";

    /**
     * 课程学生学号表别名
     */
    public static String DOWNLOAD_NAME_TMP1 = "student-numbers.xlsx";

    /**
     * 题型，单选题1，多选题2，判断题3，填空题4，主观题5
     */
    public static Integer[] TOPIC_TYPE = {1,2,3,4,5};

}
