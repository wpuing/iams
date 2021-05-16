package com.iams.common.util;

import com.alibaba.fastjson.JSONObject;
import com.iams.common.constant.IamsConstants;
import com.iams.core.dto.*;
import com.iams.core.dto.assginment.ChoiceDto;
import com.iams.core.dto.assginment.TopicDto;
import com.iams.core.dto.mcq.Choice;
import com.iams.core.dto.mcq.MultipleChoice;
import com.iams.core.dto.scores.AssignmentScoresDetails;
import com.iams.core.dto.scores.ChoiceScoresDto;
import com.iams.core.dto.scores.TopicScoresDto;
import com.iams.core.dto.student.AssignmentDetails;
import com.iams.core.dto.student.AssignmentStudentDetails;
import com.iams.core.pojo.Employee;
import com.iams.core.pojo.RoleEnum;
import com.iams.core.pojo.Student;
import com.iams.core.pojo.Teacher;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wei yz
 * @ClassName: IamsUtils
 * @Description: 工具类
 * @date 2021/2/1 17:50
 */
public class IamsUtils {

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    /**
     * 生成8位UUID大写随机码，截取UUID前8个，进行字母转换
     *
     * @return 大写的8位随机码
     */
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        // 得到随机值
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        // 转换大写
        uuid = shortBuffer.toString().toUpperCase();
        return uuid;
    }


    /**
     * 生成16位UUID随机码
     *
     * @return 16位随机码
     */
    public static String getRandomIdByUtil() {
        String randomId = UUID.randomUUID().toString().replace("-", "");
        return randomId;
    }


    /**
     * 密码加密
     *
     * @param password 密码
     * @param salt     盐值
     * @return
     */
    public static String getMD5Value(String password, String salt) {
        Object hash = new SimpleHash("MD5", password.trim(), salt.trim(), 49);
        return hash.toString();
    }


    /**
     * 正则校验手机号
     *
     * @param mobile 手机号
     * @return true为正确
     */
    public static boolean isMobile(String mobile) {
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    /**
     * 提取子串
     *
     * @param str 串，以,为为分隔
     * @return
     */
    public static List<String> getSubString(String str) {
        List<String> list = new ArrayList<>();
        for (String string : str.split(",")) {
            if (string != null && !StringUtils.isEmpty(string.trim())) {
                list.add(string);
            }
        }
        return list;
    }

    /**
     * 拼接字符串
     *
     * @param list
     * @return
     */
    public static String getString(List<String> list) {
        StringBuffer sb = new StringBuffer();
        for (String str : list) {
            sb.append(str).append(",");
        }
        return sb.deleteCharAt(sb.length() - 1).toString();//去掉最后一个逗号
    }

    /**
     * 提取答案
     *
     * @param list
     * @return
     */
    public static List<String> extract(List<Choice> list) {
        List<String> numbers = new ArrayList<>();
        for (Choice choice : list) {
            if (choice.getIsKey() == 1) {
                numbers.add(choice.getNumber());
            }
        }
        return numbers;
    }

    /**
     * 客观题题转换选择题
     *
     * @param list 客观题
     * @return
     */
    public static List<ChoiceDto> convert(List<TopicDto> list) {
        List<ChoiceDto> choiceDtoList = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (TopicDto topicDto : list) {
                ChoiceDto choiceDto = new ChoiceDto();//创建选择题视图类
                BeanUtils.copyProperties(topicDto, choiceDto);//拷贝数据
                MultipleChoice multipleChoice = JSONObject.parseObject(topicDto.getTitle(), MultipleChoice.class);//解析题目
                choiceDto.setMultipleChoice(multipleChoice);//将题目放到视图类
                choiceDtoList.add(choiceDto);//添加到集合
            }
        }
        return choiceDtoList;
    }

    /**
     * 拼接题目，如：1、java的源文件的后缀名是什么？（5分）
     *
     * @param ads 作业信息
     */
    public static void spliceTitle(AssignmentDetails ads) {
        //总长度=选择题长度+填空题+判断题+主观题
        Integer totalLength = 0;
        for (ChoiceDto choiceDto : ads.getSingleChoiceList()) {
            totalLength++;
            choiceDto.getMultipleChoice().setTitle(totalLength + "、" + " (" + choiceDto.getScore() + " 分)  " + choiceDto.getMultipleChoice().getTitle());
        }
        for (ChoiceDto choiceDto : ads.getMultipleChoiceList()) {
            totalLength++;
            choiceDto.getMultipleChoice().setTitle(totalLength + "、" + " (" + choiceDto.getScore() + " 分)  " + choiceDto.getMultipleChoice().getTitle());
        }
        for (TopicDto topic : ads.getJudgeTopicList()) {
            totalLength++;
            topic.setTitle(totalLength + "、" + " (" + topic.getScore() + "  分)  " + topic.getTitle());
        }
        for (TopicDto topic : ads.getCompletionTopicList()) {
            totalLength++;
            topic.setTitle(totalLength + "、" + " (" + topic.getScore() + "  分)  " + topic.getTitle());
        }
        for (TopicDto topic : ads.getSubTopicList()) {
            totalLength++;
            topic.setTitle(totalLength + "、" + " (" + topic.getScore() + "  分)  " + topic.getTitle());
        }
    }

    /**
     * 学生作业拼接题目，如：1、java的源文件的后缀名是什么？（5分）
     *
     * @param ads 作业信息
     */
    public static void spliceTitle(AssignmentStudentDetails ads) {
        //总长度=选择题长度+填空题+判断题+主观题
        Integer totalLength = 0;
        for (ChoiceDto choiceDto : ads.getSingleChoiceList()) {
            totalLength++;
            choiceDto.getMultipleChoice().setTitle(totalLength + "、" + " (" + choiceDto.getScore() + " 分)  " + choiceDto.getMultipleChoice().getTitle());
        }
        for (ChoiceDto choiceDto : ads.getMultipleChoiceList()) {
            totalLength++;
            choiceDto.getMultipleChoice().setTitle(totalLength + "、" + " (" + choiceDto.getScore() + " 分)  " + choiceDto.getMultipleChoice().getTitle());
        }
        for (TopicDto topic : ads.getJudgeTopicList()) {
            totalLength++;
            topic.setTitle(totalLength + "、" + " (" + topic.getScore() + "  分)  " + topic.getTitle());
        }
        for (TopicDto topic : ads.getCompletionTopicList()) {
            totalLength++;
            topic.setTitle(totalLength + "、" + " (" + topic.getScore() + "  分)  " + topic.getTitle());
        }
        for (TopicDto topic : ads.getSubTopicList()) {
            totalLength++;
            topic.setTitle(totalLength + "、" + " (" + topic.getScore() + "  分)  " + topic.getTitle());
        }
    }

    /**
     * 学生作业拼接题目，如：1、java的源文件的后缀名是什么？（5分）
     *
     * @param ads 作业信息
     */
    public static void spliceTitle(AssignmentScoresDetails ads) {
        //总长度=选择题长度+填空题+判断题+主观题
        Integer totalLength = 0;
        List<ChoiceScoresDto> singleChoiceList = ads.getSingleChoiceList();
        for (ChoiceScoresDto choiceScoresDto : singleChoiceList) {
            totalLength++;
            choiceScoresDto.getChoiceDto().getMultipleChoice().setTitle(totalLength + "、" + " (" + choiceScoresDto.getChoiceDto().getScore() + " 分)  " +
                    choiceScoresDto.getChoiceDto().getMultipleChoice().getTitle());
        }
        for (ChoiceScoresDto choiceScoresDto : ads.getMultipleChoiceList()) {
            totalLength++;
            choiceScoresDto.getChoiceDto().getMultipleChoice().setTitle(totalLength + "、" + " (" + choiceScoresDto.getChoiceDto().getScore() + " 分)  " +
                    choiceScoresDto.getChoiceDto().getMultipleChoice().getTitle());
        }
        for (TopicScoresDto topic : ads.getJudgeTopicList()) {
            totalLength++;
            topic.getTopicDto().setTitle(totalLength + "、" + " (" + topic.getTopicDto().getScore() + "  分)  " + topic.getTopicDto().getTitle());
        }
        for (TopicScoresDto topic : ads.getCompletionTopicList()) {
            totalLength++;
            topic.getTopicDto().setTitle(totalLength + "、" + " (" + topic.getTopicDto().getScore() + "  分)  " + topic.getTopicDto().getTitle());
        }
        for (TopicScoresDto topic : ads.getSubTopicList()) {
            totalLength++;
            topic.getTopicDto().setTitle(totalLength + "、" + " (" + topic.getTopicDto().getScore() + "  分)  " + topic.getTopicDto().getTitle());
        }
    }


    public static String spliceAssignmentFileUrl(String name) {
        if (Utils.isEmpty(name)) {
            return IamsConstants.ASSIGNMENT_PATH + name;
        }
        return name;
    }

    /**
     * 树形结构排序
     *
     * @param parentId           父节点ID
     * @param itemCatsBeforeList 源数据    原始查询的数据
     * @param itemCatsAfterList  目标数据   新创建的集合
     * @return
     */
    public static List<PermissionBo> sort(Integer parentId, List<PermissionBo> itemCatsBeforeList, List<PermissionBo> itemCatsAfterList) {
        for (PermissionBo entity : itemCatsBeforeList) {
            if (entity.getParentId().equals(parentId)) {
                itemCatsAfterList.add(entity);
                sort(entity.getId(), itemCatsBeforeList, itemCatsAfterList);
            }
        }
        return itemCatsAfterList;
    }

    /**
     * 提取第一个参数
     *
     * @param userInfo     用户输入的信息
     * @param studentList  学生列表
     * @param teacherList  教师列表
     * @param employeeList 员工列表
     * @param flag         false为学生||教师||员工信息 true为提取的用户信息
     * @return
     */
    public static Object extract(UserInfo userInfo, List<Student> studentList, List<Teacher> teacherList, List<Employee> employeeList, boolean flag) {
        if (!ObjectUtils.isEmpty(userInfo)) {
            if (!CollectionUtils.isEmpty(studentList)) {
                Student student = studentList.get(0); //提取第一个参数
                BeanUtils.copyProperties(student, userInfo);//拷贝
                userInfo.setUsername(getAccount(student.getNumber(), student.getEmail(), userInfo.getUsername()));//替换
                if (!flag) {
                    StudentDto studentDto = new StudentDto();
                    BeanUtils.copyProperties(student, studentDto);//拷贝
                    return studentDto;//去敏感数据
                }
                return userInfo;
            }
            if (!CollectionUtils.isEmpty(teacherList)) {
                Teacher teacher = teacherList.get(0); //提取第一个参数
                BeanUtils.copyProperties(teacher, userInfo);//拷贝
                userInfo.setUsername(getAccount(teacher.getNumber(), teacher.getEmail(), userInfo.getUsername()));//替换
                if (!flag) {
                    TeacherDto teacherDto = new TeacherDto();
                    BeanUtils.copyProperties(teacher, teacherDto);//拷贝
                    return teacherDto;//去敏感数据
                }
                return userInfo;
            }
            if (!CollectionUtils.isEmpty(employeeList)) {
                Employee employee = employeeList.get(0); //提取第一个参数
                BeanUtils.copyProperties(employee, userInfo);//拷贝
                userInfo.setUsername(getAccount(employee.getNumber(), employee.getEmail(), userInfo.getUsername()));//替换
                if (!flag) {
                    EmployeeDto employeeDto = new EmployeeDto();
                    BeanUtils.copyProperties(employee, employeeDto);//拷贝
                    employeeDto.setRoleName(RoleEnum.getRole(employee.getRoleId()).getName());//添加角色名
                    return employeeDto;//去敏感数据
                }
                return userInfo;
            }
        }
        return null;
    }

    /**
     * 获取账号名
     *
     * @param username 用户名
     * @param email    邮箱
     * @param account  用户输入的账号信息
     * @return
     */
    private static String getAccount(String username, String email, String account) {
        if (account.equals(username)) {
            return username;
        }
        if (account.equals(email)) {
            return email;
        }
        return null;
    }

    /**
     * 去除,号
     * @param str 字符串
     * @return
     */
    public static String remover(String str) {
        return str.replace(",", "");

    }

}
