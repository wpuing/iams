package com.iams.common.util;

import com.iams.common.exception.ParameterException;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Wei yz
 * @ClassName: Utils
 * @Description:
 * @date 2021/2/2 18:48
 */
public class Utils {


    public static void isEmpty(Integer number, String message) {
        if (!isEmpty(number)) {
            throw new ParameterException(message);
        }
    }

    public static void isEmpty(String str, String message) {
        if (str == null || StringUtils.isEmpty(str.trim())) {
            if (message == null || StringUtils.isEmpty(message.trim())) {
                throw new ParameterException("输入的字符为空");
            } else {
                throw new ParameterException(message);
            }
        }
    }

    public static boolean isEmpty(String str) {
        if (str == null || StringUtils.isEmpty(str.trim())) {
            return false;
        }
        return true;
    }

    public static boolean isEmpty(Integer i) {
        if (i != null && i > 0) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Float f) {
        if (f != null && f > 0.00) {
            return true;
        }
        return false;
    }


    /**
     * 截取字符串并提取数字
     *
     * @param str 带数字的字符串,以,分隔
     * @return 提取后的整数集合
     */
    public static List<Integer> getIds(String str) {
        isEmpty(str, "字符数据不能为空");
        List<Integer> list = new ArrayList<Integer>();
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        String[] strs = str.split(",");
        for (int i = 0, len = strs.length; i < len; i++) {
            Matcher m = p.matcher(strs[i].toString());
            list.add(Integer.parseInt(m.replaceAll("").trim()));
        }
        return list;
    }


    /**
     * 获得当前时间
     *
     * @return
     */
    public static String getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

}
